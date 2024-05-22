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

data class LineageInfo(
    val parents: JsonArray?,
    val children: JsonArray?,
    val node: String,
)

fun LineageInfo.toJson(): JsonObject {
    val json = JsonObject()
    json.addProperty("node", node)
    json.add("parents", parents)
    json.add("children", children)
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

    fun getLineageInfoForNode(node: String): LineageInfo? {
        manifestLock.withLock {
            if (manifest == null) {
                parseManifest()
            }
            val children = manifest!!.getAsJsonObject("child_map").getAsJsonArray(node)
            val parents = manifest!!.getAsJsonObject("parent_map").getAsJsonArray(node)
            if (parents == null && children == null) {
                return null
            }
            return LineageInfo(parents, children, node)
        }
    }
}
