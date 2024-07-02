package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import java.util.concurrent.CountDownLatch

@Service(Service.Level.PROJECT)
class DbtCommandExecutorService(private var project: Project) {
    private val venvInitializerService = project.service<VenvInitializerService>()
    private val processExecutorService = project.service<ProcessExecutorService>()
    private val outputHandler = project.service<ProcessOutputHandlerService>()

    private fun showLoadingIndicator(
        title: String,
        task: () -> Unit,
    ) {
        val backgroundTask =
            object : Task.Backgroundable(project, title, false) {
                override fun run(indicator: ProgressIndicator) {
                    indicator.isIndeterminate = true
                    task.invoke()
                }
            }
        ProgressManager.getInstance().run(backgroundTask)
    }

    @Synchronized
    fun executeCommand(command: List<String>) {
        val latch = CountDownLatch(1)

        showLoadingIndicator("Executing dbt ${command.joinToString(" ")}...") {
            try {
                if (!venvInitializerService.isInitialized) {
                    venvInitializerService.initializeEnvironment()
                }
                val process = processExecutorService.executeCommand(command)
                outputHandler.handleOutput(process)
            } finally {
                latch.countDown()
            }
        }

        latch.await()
    }
}
