package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class ProcessExecutorService(private var project: Project) {
    private val settings = project.service<DbtToolkitSettingsService>()
    private val venvInitializerService = project.service<VenvInitializerService>()
    private val loggingService = project.service<LoggingService>()

    fun executeCommand(command: List<String>): Process {
        val processBuilder = getDbtProcessBuilder(command)
        loggingService.log(">>> ${processBuilder.command().joinToString(" ")}\n", ConsoleViewContentType.NORMAL_OUTPUT)
        processBuilder.directory(settings.state.settingsDbtProjectDir.let { File(it) })
        return processBuilder.start()
    }

    private fun getDbtProcessBuilder(command: List<String>): ProcessBuilder {
        val processBuilder = ProcessBuilder()
        val env = processBuilder.environment()
        settings.state.settingsEnvVars.forEach { (k, v) -> env[k] = v }
        val venvDbtPath = venvInitializerService.getDbtPath()
        if (venvDbtPath != null) {
            // venv
            return processBuilder.command(venvDbtPath, "--no-use-colors", *command.toTypedArray())
        }
        // global
        return processBuilder.command("dbt", "--no-use-colors", *command.toTypedArray())
    }
}
