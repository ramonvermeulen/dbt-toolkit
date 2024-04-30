package com.github.ramonvermeulen.dbtidea.services

import com.intellij.execution.impl.ConsoleViewImpl
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.execution.ui.ConsoleViewContentType.NORMAL_OUTPUT
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer

@Service(Service.Level.PROJECT)
class LoggingService(private val project: Project) {
    val consoleView: ConsoleView = ConsoleViewImpl(project, false)

    init {
        consoleView.print(
            "dbtIdea executes dbt commands under the hood for generating artifacts,\n" +
                "in case something went wrong during background execution you will see the error message printed " +
                "in this console.\n",
            NORMAL_OUTPUT,
        )
    }

    fun log(
        message: String,
        type: ConsoleViewContentType,
    ) {
        consoleView.print(message, type)
    }

    fun clearConsole() {
        consoleView.clear()
    }

    fun dispose() {
        Disposer.dispose(consoleView)
    }
}
