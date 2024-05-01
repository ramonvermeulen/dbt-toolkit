package com.github.ramonvermeulen.dbtidea.services

import com.github.ramonvermeulen.dbtidea.actions.OpenConsoleAction
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.notification.NotificationType
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

    @Synchronized
    fun executeCommand(command: List<String>) {
        val processBuilder = ProcessBuilder("dbt", "--no-use-colors", *command.toTypedArray())
        loggingService.log(">>> dbt --no-use-colors ${command.joinToString(" ")}\n", ConsoleViewContentType.NORMAL_OUTPUT)
        processBuilder.directory(settings.state.settingsDbtProjectDir.let { File(it) })

        var stdout = ""
        try {
            val env = processBuilder.environment()
            settings.state.settingsEnvVars.forEach{ (k, v) -> env[k] = v }

            val process = processBuilder.start()
            val exitCode = if (process.waitFor(20, TimeUnit.SECONDS)) {
                process.exitValue()
            } else {
                process.destroy()
                -1
            }
            // dbt only uses stdout, even for errors
            stdout = process.inputStream.bufferedReader().readText() + "\n"
            if (exitCode != 0) {
                loggingService.log(stdout, ConsoleViewContentType.ERROR_OUTPUT)
                notificationService.sendNotification(
                    "dbtIdeaNotificationGroup",
                    "dbt command in the background failed, check the console tab to see details",
                    NotificationType.ERROR,
                    OpenConsoleAction(),
                )
            } else {
                loggingService.log(stdout, ConsoleViewContentType.NORMAL_OUTPUT)
            }
        } catch (e: Exception) {
            loggingService.log("Error executing dbt command: ${e.message}", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }
}
