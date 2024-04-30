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
        dbtCommandExecutorService.executeCommand(listOf("docs", "generate"))
        val docs = File("${settings.state.dbtTargetDir}/${settings.static.DBT_DOCS_FILE}")
        return docs
    }
}
