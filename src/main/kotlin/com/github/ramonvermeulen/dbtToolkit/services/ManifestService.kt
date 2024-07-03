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
class ManifestService(private var project: Project) {
    private val lineageInfoService = project.service<LineageInfoService>()
    private var settingsService = project.service<DbtToolkitSettingsService>()
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private var manifest: JsonObject? = null
    private val manifestLock = ReentrantLock()

    @Synchronized
    private fun parseManifest() {
        dbtCommandExecutorService.executeCommand(listOf("parse", "--no-partial-parse"))
        val file = File("${settingsService.state.settingsDbtTargetDir}/manifest.json")
        if (file.exists()) {
            manifest = JsonParser.parseString(file.readText()).asJsonObject
        } else {
            println("Manifest file does not exist")
            println("looked in the following path: ${settingsService.state.settingsDbtTargetDir}/manifest.json")
        }
    }

    // todo(ramon) reconsider this implementation, might be improved
    private fun getCompleteLineageForNode(node: String) {
        val visited = mutableSetOf<String>()
        val nodes = mutableSetOf<Node>()
        val relationships = mutableSetOf<Edge>()
        val manifestNodes = manifest!!.getAsJsonObject("nodes")
        val manifestSources = manifest!!.getAsJsonObject("sources")

        fun depthFirstSearch(
            node: String,
            initialNode: Boolean = false,
        ) {
            if (node in visited) return
            val tests = mutableListOf<String>()
            visited.add(node)

            val children = manifest!!.getAsJsonObject("child_map").getAsJsonArray(node)
            val parents = manifest!!.getAsJsonObject("parent_map").getAsJsonArray(node)

            val nodePath: String =
                if (manifestNodes.has(node)) {
                    manifestNodes.getAsJsonObject(node).get("original_file_path").asString
                } else if ("source" in node) {
                    manifestSources.getAsJsonObject(node).get("original_file_path").asString
                } else {
                    ""
                }

            children?.forEach { child ->
                if (child.asString.startsWith("test.")) {
                    tests.add(child.asString)
                    return@forEach
                }
                depthFirstSearch(child.asString)
                relationships.add(Edge(node, child.asString))
            }
            parents?.forEach { parent ->
                depthFirstSearch(parent.asString)
                relationships.add(Edge(parent.asString, node))
            }

            nodes.add(Node(node, isSelected = initialNode, tests = tests, relativePath = nodePath))
        }

        depthFirstSearch(node, true)
        lineageInfoService.setLineageInfo(lineageInfo = LineageInfo(nodes = nodes, edges = relationships))
    }

    fun refreshLineageInfoForNode(
        node: String,
        enforceReparse: Boolean,
    ) {
        manifestLock.withLock {
            if (enforceReparse || manifest == null ||
                !(manifest!!.getAsJsonObject("nodes").has(node) || manifest!!.getAsJsonObject("sources").has(node))
            ) {
                parseManifest()
            }
            getCompleteLineageForNode(node)
        }
    }
}
