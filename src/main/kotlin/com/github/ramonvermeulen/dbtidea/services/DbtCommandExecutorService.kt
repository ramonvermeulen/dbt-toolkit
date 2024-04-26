package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    fun executeCommand(command: List<String>): String {
        val processBuilder = ProcessBuilder("dbt", *command.toTypedArray())
        processBuilder.directory(project.basePath?.let { File(it) })
        val process = processBuilder.start()
        val exitCode = process.waitFor()
        val stdout = process.inputStream.bufferedReader().readText()
        val stderr = process.errorStream.bufferedReader().readText()
        if (exitCode != 0) {
            println(stderr)
            throw Exception("Command failed with exit code $exitCode")
        }
        return stdout
    }
}