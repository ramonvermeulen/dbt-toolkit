package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    val settings = project.service<DbtIdeaSettingsService>()
    fun executeCommand(command: List<String>): String {
        val processBuilder = ProcessBuilder("dbt", *command.toTypedArray())
        processBuilder.directory(settings.state.dbtProjectDir.let { File(it) })
        val process = processBuilder.start()
        val exitCode = process.waitFor()
        val stdout = process.inputStream.bufferedReader().readText()
        val stderr = process.errorStream.bufferedReader().readText()
        if (exitCode != 0) {
            println(stderr)
            println(stdout)
//            throw Exception("Command failed with exit code $exitCode")
        }
        return stdout
    }
}
