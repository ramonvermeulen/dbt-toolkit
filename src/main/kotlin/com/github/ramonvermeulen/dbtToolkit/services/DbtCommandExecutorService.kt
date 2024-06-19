package com.github.ramonvermeulen.dbtToolkit.services

import com.github.ramonvermeulen.dbtToolkit.actions.OpenConsoleAction
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ProjectRootManager
import java.io.File
import java.util.concurrent.TimeUnit
import com.jetbrains.python.sdk.PythonSdkUtil

@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    private val settings = project.service<DbtToolkitSettingsService>()
    private val loggingService = project.service<LoggingService>()
    private val notificationService = project.service<NotificationService>()

    fun getPythonInterpreterPath(project: Project): String? {
        val projectSdk: Sdk? = ProjectRootManager.getInstance(project).projectSdk
        return if (projectSdk != null && PythonSdkUtil.isPythonSdk(projectSdk)) {
            projectSdk.homePath?.let { PythonSdkUtil.getPythonExecutable(it) }
        } else {
            null
        }
    }

    @Synchronized
    fun executeCommand(command: List<String>) {
        val cmd = listOf("dbt", "--no-use-colors", *command.toTypedArray())
        val processBuilder = ProcessBuilder(cmd)
        loggingService.log(">>> ${cmd.joinToString(" ")}\n", ConsoleViewContentType.NORMAL_OUTPUT)
        processBuilder.directory(settings.state.settingsDbtProjectDir.let { File(it) })

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
            val stdout = process.inputStream.bufferedReader().readText() + "\n"
            if (exitCode != 0) {
                loggingService.log(stdout, ConsoleViewContentType.ERROR_OUTPUT)
                notificationService.sendNotification(
                    "dbtToolkitNotificationGroup",
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
