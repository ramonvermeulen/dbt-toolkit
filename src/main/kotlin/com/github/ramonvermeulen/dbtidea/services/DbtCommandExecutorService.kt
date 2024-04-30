package com.github.ramonvermeulen.dbtidea.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File
import java.util.concurrent.TimeUnit

@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    private val settings = project.service<DbtIdeaSettingsService>()
    private val loggingService = project.service<LoggingService>()
    private val notificationService = project.service<NotificationService>()

    fun executeCommand(command: List<String>) {
        val processBuilder = ProcessBuilder("dbt", *command.toTypedArray())
        processBuilder.directory(settings.state.dbtProjectDir.let { File(it) })
        var stdout = ""
        try {
            val process = processBuilder.start()
            val exitCode = if (process.waitFor(20, TimeUnit.SECONDS)) {
                process.exitValue()
            } else {
                process.destroy()
                -1
            }
            stdout = process.inputStream.bufferedReader().readText()
            if (exitCode != 0) {
                loggingService.flush()
                loggingService.log(stdout, ConsoleViewContentType.ERROR_OUTPUT)
                notificationService.sendNotification("dbt command in the background failed", "check the console tab to see details")
            } else {
                loggingService.log(stdout, ConsoleViewContentType.NORMAL_OUTPUT)
            }
        } catch (e: Exception) {
            loggingService.log("Error executing dbt command: ${e.message}", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }
}
