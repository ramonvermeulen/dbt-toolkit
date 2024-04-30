package com.github.ramonvermeulen.dbtidea.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

data class LineageInfo(
    val parents: JsonArray?,
    val children: JsonArray?,
    val node: String,
)

@Service(Service.Level.PROJECT)
class ManifestService(private var project: Project) {
    private var settings = project.service<DbtIdeaSettingsService>()
    private var interval = 5 * 60 * 1000 // 5 minutes
    private var lastUpdateTimestamp = System.currentTimeMillis() - interval // initial set now - 5 minutes
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private var manifest: JsonObject? = null

    private fun parseManifest() {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastUpdateTimestamp
        if (elapsedTime > interval) {
            lastUpdateTimestamp = currentTime
            dbtCommandExecutorService.executeCommand(listOf("parse"))
        }
        val file = File("${settings.state.dbtTargetDir}/manifest.json")
        if (file.exists()) {
            manifest = JsonParser.parseString(file.readText()).asJsonObject
        } else {
            println("Manifest file does not exist")
            println("looked in the following path: ${settings.state.dbtTargetDir}/manifest.json")
        }
    }

    fun getLineageInfoForNode(node: String): LineageInfo? {
        if (manifest == null) {
            parseManifest()
            return null
        }
        val parents = manifest!!.getAsJsonObject("child_map").getAsJsonArray(node)
        val children = manifest!!.getAsJsonObject("parent_map").getAsJsonArray(node)
        if (parents == null && children == null) {
            return null
        }
        return LineageInfo(parents, children, node)
    }
}
