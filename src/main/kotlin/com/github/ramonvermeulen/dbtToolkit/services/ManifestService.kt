package com.github.ramonvermeulen.dbtToolkit.services

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
    val relativePath: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        // isSelected is not considered in the equality check
        // to prevent unnecessary re-renders of the UI lineage graph
        if (id != other.id) return false
        if (tests != other.tests) return false
        if (relativePath != other.relativePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + tests.hashCode()
        result = 31 * result + relativePath.hashCode()
        return result
    }
}

fun Node.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("id", id)
    json.addProperty("isSelected", isSelected)
    json.add("tests", JsonArray().apply { tests.forEach { add(it) } })
    json.addProperty("relativePath", relativePath)
    return json
}

data class Edge(
    val parent: String,
    val child: String,
)

fun Edge.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("parent", parent)
    json.addProperty("child", child)
    return json
}

data class LineageInfo(
    val nodes: MutableList<Node>,
    val edges: MutableList<Edge>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineageInfo

        if (nodes.toSet() != other.nodes.toSet()) return false
        if (edges.toSet() != other.edges.toSet()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nodes.toSet().hashCode()
        result = 31 * result + edges.toSet().hashCode()
        return result
    }
}

fun LineageInfo.toJson(): JsonObject {
    val json = JsonObject() // t.b.d.
    json.add("nodes", JsonArray().apply { nodes.forEach { add(it.toJson()) } })
    json.add("edges", JsonArray().apply { edges.forEach { add(it.toJson()) } })
    return json
}

@Service(Service.Level.PROJECT)
class ManifestService(private var project: Project) {
    private var settings = project.service<DbtToolkitSettingsService>()
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

    private fun getCompleteLineageForNode(node: String): LineageInfo {
        val visited = mutableSetOf<String>()
        val nodes = mutableListOf<Node>()
        val relationships = mutableListOf<Edge>()
        val manifestNodes = manifest!!.getAsJsonObject("nodes")
        val manifestSources = manifest!!.getAsJsonObject("sources")

        fun depthFirstSearch(node: String, initialNode: Boolean = false) {
            if (node in visited) return
            val tests = mutableListOf<String>()
            visited.add(node)
            var nodePath = ""

            val children = manifest!!.getAsJsonObject("child_map").getAsJsonArray(node)
            val parents = manifest!!.getAsJsonObject("parent_map").getAsJsonArray(node)

            if (manifestNodes.has(node)) {
                nodePath = manifestNodes.getAsJsonObject(node).get("original_file_path").asString
            }

            if ("source" in node) {
                nodePath = manifestSources.getAsJsonObject(node).get("original_file_path").asString
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
        print(node)
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
