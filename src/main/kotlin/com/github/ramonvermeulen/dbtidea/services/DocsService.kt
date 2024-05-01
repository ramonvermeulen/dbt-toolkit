package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DocsService(private var project: Project) {
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private val settings = project.service<DbtIdeaSettingsService>()

    fun getDocs(forceRegen: Boolean = false): File {
        val docs = File("${settings.state.settingsDbtTargetDir}/${settings.static.DBT_DOCS_FILE}")
        if (docs.exists() && !forceRegen) {
            return docs
        }
        dbtCommandExecutorService.executeCommand(listOf("docs", "generate"))
        return docs
    }
}
