package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DocsService(private var project: Project) {
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private val settings = project.service<DbtIdeaSettingsService>()

    fun getDocs(): File {
        // apply smarter logic
        val docs = File(settings.state.dbtTargetDir + "/index.html")
        val manifest = File(settings.state.dbtTargetDir + "/manifest.json")
        val catalog = File(settings.state.dbtTargetDir + "/catalog.json")
        if (!docs.exists() || !manifest.exists() || !catalog.exists()) {
            dbtCommandExecutorService.executeCommand(listOf("docs", "generate"))
        }
        return docs
    }
}
