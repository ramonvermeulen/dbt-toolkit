package com.github.ramonvermeulen.dbtToolkit.services

import com.github.ramonvermeulen.dbtToolkit.DBT_DOCS_FILE
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DocsService(private var project: Project) {
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private val settings = project.service<DbtToolkitSettingsService>()

    fun getDocs(forceRegen: Boolean = false): File {
        val docs = File("${settings.state.settingsDbtTargetDir}/${DBT_DOCS_FILE}")
        if (docs.exists() && !forceRegen) {
            return docs
        }
        dbtCommandExecutorService.executeCommand(listOf("docs", "generate"))
        return docs
    }
}
