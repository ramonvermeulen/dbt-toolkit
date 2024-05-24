package com.github.ramonvermeulen.dbtidea.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

data class Node(
    val id: String,
    val tests: MutableList<String> = mutableListOf(),
    val isSelected: Boolean = false,
)

fun Node.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("id", id)
    json.addProperty("isSelected", isSelected)
    json.add("tests", JsonArray().apply { tests.forEach { add(it) } })
    return json
}

data class Edge(
    val parent: Node,
    val child: Node,
)

fun Edge.toJson(): JsonObject {
    val json = JsonObject()
    json.add("parent", parent.toJson())
    json.add("child", child.toJson())
    return json
}

data class LineageInfo(
    val nodes: MutableList<Node>,
    val edges: MutableList<Edge>,
)

fun LineageInfo.toJson(): JsonObject {
    val json = JsonObject() // t.b.d.
    json.add("nodes", JsonArray().apply { nodes.forEach { add(it.toJson()) } })
    json.add("edges", JsonArray().apply { edges.forEach { add(it.toJson()) } })
    return json
}

@Service(Service.Level.PROJECT)
class ManifestService(private var project: Project) {
    private var settings = project.service<DbtIdeaSettingsService>()
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private var manifest: JsonObject? = null
    private val manifestLock = ReentrantLock()

    private fun parseManifest() {
        dbtCommandExecutorService.executeCommand(listOf("parse"))
        val file = File("${settings.state.settingsDbtTargetDir}/manifest.json")
        if (file.exists()) {
            manifest = JsonParser.parseString(file.readText()).asJsonObject
        } else {
            println("Manifest file does not exist")
            println("looked in the following path: ${settings.state.settingsDbtTargetDir}/manifest.json")
        }
    }

    private fun getCompleteLineageForNode(node: String): LineageInfo? {
        val visited = mutableSetOf<String>()
        val nodes = mutableListOf<Node>()
        val relationships = mutableListOf<Edge>()

        fun depthFirstSearch(node: String, initialNode: Boolean = false) {
            if (node in visited) return
            val tests = mutableListOf<String>()
            visited.add(node)

            val children = manifest!!.getAsJsonObject("child_map").getAsJsonArray(node)
            val parents = manifest!!.getAsJsonObject("parent_map").getAsJsonArray(node)

            children?.forEach { child ->
                if (child.asString.startsWith("test.")) {
                    tests.add(child.asString)
                    return@forEach
                }
                depthFirstSearch(child.asString)
                relationships.add(Edge(Node(node), Node(child.asString)))
            }
            parents?.forEach { parent ->
                depthFirstSearch(parent.asString)
                relationships.add(Edge(Node(parent.asString), Node(node)))
            }

            nodes.add(Node(node, isSelected = initialNode, tests = tests))
        }

        depthFirstSearch(node, true)

        return LineageInfo(nodes, relationships)
    }

    fun getLineageInfoForNode(node: String): LineageInfo? {
        manifestLock.withLock {
            if (manifest == null) {
                parseManifest()
            }
            return getCompleteLineageForNode(node)
        }
    }
}
