package com.github.ramonvermeulen.dbtToolkit.services

import com.github.ramonvermeulen.dbtToolkit.models.Edge
import com.github.ramonvermeulen.dbtToolkit.models.LineageInfo
import com.github.ramonvermeulen.dbtToolkit.models.Node
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Service(Service.Level.PROJECT)
class ManifestService(project: Project) {
    private val lineageInfoService = project.service<LineageInfoService>()
    private var settingsService = project.service<DbtToolkitSettingsService>()
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private var manifest: JsonObject? = null
    private val manifestLock = ReentrantLock()

    @Synchronized
    private fun parseManifest() {
        dbtCommandExecutorService.executeCommand(listOf("parse"))
        val file = File("${settingsService.state.settingsDbtTargetDir}/manifest.json")
        if (file.exists()) {
            manifest = JsonParser.parseString(file.readText()).asJsonObject
        } else {
            println("Manifest file does not exist")
            println("looked in the following path: ${settingsService.state.settingsDbtTargetDir}/manifest.json")
        }
    }

    // todo(ramon) reconsider this implementation, might be improved
    // not writing tests yet because I'm not sure if I want to stick with this implementation
    private fun getLineageForNode(node: String, completeLineage: Boolean) {
        val visited = mutableSetOf<String>()
        val nodes = mutableSetOf<Node>()
        val relationships = mutableSetOf<Edge>()

        fun depthFirstSearch(
            node: String,
            initialNode: Boolean = false,
            resolveChildren: Boolean = true,
            resolveParents: Boolean = true,
        ) {
            if (node in visited) return
            visited.add(node)

            manifest?.run {
                val manifestNodes = getAsJsonObject("nodes")
                val manifestSources = getAsJsonObject("sources")
                val children = getAsJsonObject("child_map").getAsJsonArray(node)
                val parents = getAsJsonObject("parent_map").getAsJsonArray(node)

                val nodePath: String =
                    when {
                        manifestNodes.has(node) -> manifestNodes.getAsJsonObject(node).get("original_file_path").asString
                        "source" in node -> manifestSources.getAsJsonObject(node).get("original_file_path").asString
                        else -> ""
                    }

                val tests =
                    children?.mapNotNull { child ->
                        child.asString.takeIf { it.startsWith("test.") }
                    }?.toSet() ?: emptySet()

                if (resolveChildren) {
                    children?.forEach { child ->
                        if (!child.asString.startsWith("test.")) {
                            depthFirstSearch(
                                child.asString,
                                initialNode = false,
                                resolveChildren = true,
                                resolveParents = completeLineage,
                            )
                            relationships.add(Edge(node, child.asString))
                        }
                    }
                }

                if (resolveParents) {
                    parents?.forEach { parent ->
                        depthFirstSearch(
                            parent.asString,
                            initialNode = false,
                            resolveChildren = completeLineage,
                            resolveParents = true,
                        )
                        relationships.add(Edge(parent.asString, node))
                    }
                }

                nodes.add(Node(node, isSelected = initialNode, tests = tests, relativePath = nodePath))
            }
        }

        depthFirstSearch(node, true)
        lineageInfoService.setLineageInfo(lineageInfo = LineageInfo(nodes = nodes, edges = relationships))
    }

    fun refreshLineageInfoForNode(
        node: String,
        enforceReparse: Boolean,
        completeLineage: Boolean,
    ) {
        manifestLock.withLock {
            if (enforceReparse || manifest == null ||
                !(manifest!!.getAsJsonObject("nodes").has(node) || manifest!!.getAsJsonObject("sources").has(node))
            ) {
                parseManifest()
            }
            getLineageForNode(node, completeLineage)
        }
    }
}
