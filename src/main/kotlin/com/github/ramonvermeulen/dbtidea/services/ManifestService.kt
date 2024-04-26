package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class ManifestService(var project: Project) {
    private var interval = 5 * 60 * 1000 // 5 minutes
    private var lastUpdateTimestamp = System.currentTimeMillis() - interval // initial set now - 5 minutes
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()

    fun parseManifest() {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastUpdateTimestamp
        if (elapsedTime > interval) {
            lastUpdateTimestamp = currentTime
            dbtCommandExecutorService.executeCommand(listOf("parse"))
        }
    }
}