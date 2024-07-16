package com.github.ramonvermeulen.dbtToolkit.services

import com.github.ramonvermeulen.dbtToolkit.actions.OpenConsoleAction
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.util.concurrent.TimeUnit

@Service(Service.Level.PROJECT)
class ProcessOutputHandlerService(project: Project) {
    private val settings = project.service<DbtToolkitSettingsService>()
    private val loggingService = project.service<LoggingService>()
    private val notificationService = project.service<NotificationService>()

    fun handleOutput(process: Process) {
        val exitCode =
            if (process.waitFor(settings.state.settingsDbtCommandTimeout, TimeUnit.SECONDS)) {
                process.exitValue()
            } else {
                process.destroy()
                loggingService.log(
                    "dbt command timeout of: ${settings.state.settingsDbtCommandTimeout} exceeded, please " +
                        "increase the dbt command timeout in the settings and try again",
                    ConsoleViewContentType.ERROR_OUTPUT,
                )
                -1
            }
        val stdout = process.inputStream.bufferedReader().readText() + "\n"
        val stderr = process.errorStream.bufferedReader().readText() + "\n"
        val output = stdout + stderr
        if (exitCode != 0) {
            loggingService.log(output, ConsoleViewContentType.ERROR_OUTPUT)
            notificationService.sendNotification(
                "dbtToolkitNotificationGroup",
                "dbt command in the background failed, check the console tab to see details",
                NotificationType.ERROR,
                OpenConsoleAction(),
            )
        } else {
            loggingService.log(stdout, ConsoleViewContentType.NORMAL_OUTPUT)
        }
    }
}
