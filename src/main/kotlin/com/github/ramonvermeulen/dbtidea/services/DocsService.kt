package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DocsService(private var project: Project) {
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()

    fun getDocs(): File {
        val file = File(project.basePath + "/target/index.html")
        if (!file.exists()) {
            dbtCommandExecutorService.executeCommand(listOf("dbt docs generate"))
        }
        return file
    }
}