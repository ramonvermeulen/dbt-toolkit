package com.github.ramonvermeulen.dbtidea.services

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class ManifestService(private var project: Project) {
    private var interval = 5 * 60 * 1000 // 5 minutes
    private var lastUpdateTimestamp = System.currentTimeMillis() - interval // initial set now - 5 minutes
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private var manifest: JsonObject? = null

    fun parseManifest() {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastUpdateTimestamp
        if (elapsedTime > interval) {
            lastUpdateTimestamp = currentTime
            dbtCommandExecutorService.executeCommand(listOf("parse"))
        }
        val file = File(project.basePath + "/target/manifest.json")
        if (file.exists()) {
            manifest = JsonParser.parseString(file.readText()).asJsonObject
        } else {
            println("Manifest file does not exist")
        }
    }
}