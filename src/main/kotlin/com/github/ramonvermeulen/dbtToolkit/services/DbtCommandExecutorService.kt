package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project


@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    private val venvInitializerService = project.service<VenvInitializerService>()
    private val processExecutorService = project.service<ProcessExecutorService>()
    private val outputHandler = project.service<ProcessOutputHandlerService>()

    @Synchronized
    fun executeCommand(command: List<String>) {
        venvInitializerService.initializeEnvironment()
        val process = processExecutorService.executeCommand(command)
        outputHandler.handleOutput(process)
    }
}