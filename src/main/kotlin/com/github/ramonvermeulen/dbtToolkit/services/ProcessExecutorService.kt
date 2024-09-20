package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class ProcessExecutorService(project: Project) {
    private val settings = project.service<DbtToolkitSettingsService>()
    private val venvInitializerService = project.service<VenvInitializerService>()
    private val loggingService = project.service<LoggingService>()
    private val dotEnvRegex =
        Regex(
            """^\s*(?:export\s+)?([\w.-]+)\s*(?:=\s*|:\s+)?(\s*'(?:\\'|[^'])*'|\s*"(?:\\"|[^"])*"|\s*`(?:\\`|[^`])*`|[^#\r\n]+)?\s*(?:#.*)?$""",
            RegexOption.MULTILINE,
        )

    fun executeCommand(command: List<String>): Process {
        val processBuilder = getDbtProcessBuilder(command)
        loggingService.log(">>> ${processBuilder.command().joinToString(" ")}\n", ConsoleViewContentType.NORMAL_OUTPUT)
        processBuilder.directory(settings.state.settingsDbtProjectDir.let { File(it) })
        return processBuilder.start()
    }

    private fun loadDotEnv(
        env: MutableMap<String, String>,
        file: File,
    ) {
        // took the popular dotenv library code as example https://github.com/motdotla/dotenv/blob/master/lib/main.js
        val dotEnvContent = file.readLines()
        for (line in dotEnvContent) {
            // convert linebreaks to same format
            line.replace(Regex("/\\r\\n?/mg"), "\n")

            dotEnvRegex.find(line)?.let { matchResult ->
                val key = matchResult.groups[1]?.value
                var value = matchResult.groups[2]?.value ?: ""

                if (key != null) {
                    // Remove surrounding whitespace
                    value = value.trim()

                    // Check if double quoted
                    val isDoubleQuoted = value.startsWith("\"") && value.endsWith("\"")

                    // Remove surrounding quotes
                    value = value.replace(Regex("""^(['"`])(.*)\1$"""), "$2")

                    // expand new lines if double quoted
                    if (isDoubleQuoted) {
                        value = value.replace("\\n", "\n")
                        value = value.replace("\\r", "\r")
                    }

                    env[key] = value
                }
            }
        }
    }

    private fun getDbtProcessBuilder(command: List<String>): ProcessBuilder {
        val processBuilder = ProcessBuilder()
        val env = processBuilder.environment()

        if (File(settings.state.settingsDotEnvFilePath).exists()) {
            loadDotEnv(env, File(settings.state.settingsDotEnvFilePath))
        } else if (settings.state.settingsDotEnvFilePath.isNotEmpty()) {
            loggingService.log(
                "Could not find .env file at ${settings.state.settingsDotEnvFilePath}\n",
                ConsoleViewContentType.LOG_WARNING_OUTPUT,
            )
        }

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
