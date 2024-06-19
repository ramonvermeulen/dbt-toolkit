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
    private var lateInit = false
    private var venvDbtPath: String? = null

    private fun lateInit() {
        val interpreterPath = getPythonInterpreterPath(project)
        if (interpreterPath != null) {
            loggingService.log("Python venv found at: $interpreterPath\n", ConsoleViewContentType.NORMAL_OUTPUT)
            val interpreterFile = File(interpreterPath)
            if (File("${interpreterFile.parent}/dbt").exists()) {
                venvDbtPath = "${interpreterFile.parent}/dbt"
                loggingService.log("dbt installation found within venv at: $venvDbtPath\n", ConsoleViewContentType.NORMAL_OUTPUT)
            } else {
                loggingService.log("No dbt installation found within venv.. please install dbt and restart your IDE", ConsoleViewContentType.NORMAL_OUTPUT)
            }
        } else {
            loggingService.log("No Python venv found, trying to use a global dbt installation", ConsoleViewContentType.NORMAL_OUTPUT)
        }
        loggingService.log("\n\n", ConsoleViewContentType.NORMAL_OUTPUT)
        lateInit = true
    }

    private fun getPythonInterpreterPath(project: Project): String? {
        val projectSdk: Sdk? = ProjectRootManager.getInstance(project).projectSdk
        if (projectSdk != null && PythonSdkUtil.isPythonSdk(projectSdk)) {
            return projectSdk.homePath?.let { PythonSdkUtil.getPythonExecutable(it) }
        }
        return null
    }

    private fun getDbtProcessBuilder(command: List<String>): ProcessBuilder {
        val processBuilder = ProcessBuilder()
        val env = processBuilder.environment()
        settings.state.settingsEnvVars.forEach{ (k, v) -> env[k] = v }
        if (venvDbtPath != null) {
            // venv
            return processBuilder.command(venvDbtPath, "--no-use-colors", *command.toTypedArray())
        }
        // global
        return processBuilder.command("dbt", "--no-use-colors", *command.toTypedArray())
    }

    @Synchronized
    fun executeCommand(command: List<String>) {
        if (!lateInit) {
            lateInit()
        }
        val processBuilder = getDbtProcessBuilder(command)
        loggingService.log(">>> ${processBuilder.command().joinToString(" ")}\n", ConsoleViewContentType.NORMAL_OUTPUT)
        processBuilder.directory(settings.state.settingsDbtProjectDir.let { File(it) })
        try {
            val process = processBuilder.start()
            val exitCode = if (process.waitFor(settings.state.settingsDbtCommandTimeout, TimeUnit.SECONDS)) {
                process.exitValue()
            } else {
                process.destroy()
                loggingService.log(
                    "dbt command timeout of: ${settings.state.settingsDbtCommandTimeout} exceeded, please " +
                            "increase the dbt command timeout in the settings and try again",
                    ConsoleViewContentType.ERROR_OUTPUT
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
        } catch (e: Exception) {
            loggingService.log("Error executing dbt command: ${e.message}", ConsoleViewContentType.ERROR_OUTPUT)
        }
    }
}
