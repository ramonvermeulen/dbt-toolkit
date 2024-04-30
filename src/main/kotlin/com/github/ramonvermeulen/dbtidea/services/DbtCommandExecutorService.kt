package com.github.ramonvermeulen.dbtidea.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    private val settings = project.service<DbtIdeaSettingsService>()
    private val loggingService = project.service<LoggingService>()
    private val notificationService = project.service<NotificationService>()

    fun executeCommand(command: List<String>): String {
        val processBuilder = ProcessBuilder("dbt", *command.toTypedArray())
        processBuilder.directory(settings.state.dbtProjectDir.let { File(it) })
        val process = processBuilder.start()
        val exitCode = process.waitFor()
        val stdout = process.inputStream.bufferedReader().readText()
        if (exitCode != 0) {
            loggingService.flush()
            loggingService.log(stdout, ConsoleViewContentType.ERROR_OUTPUT)
            notificationService.sendNotification("dbt command in the background failed", "check the console tab to see details")
        }
        return stdout
    }
}
