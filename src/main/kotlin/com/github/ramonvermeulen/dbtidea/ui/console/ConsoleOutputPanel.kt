package com.github.ramonvermeulen.dbtidea.ui.console

import com.github.ramonvermeulen.dbtidea.services.LoggingService
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class ConsoleOutputPanel(private val project: Project, private val toolWindow: ToolWindow) : Disposable {
    private val loggingService = project.service<LoggingService>()
    private val mainPanel: JPanel = JPanel(BorderLayout())

    init {
        SwingUtilities.invokeLater {
            mainPanel.add(loggingService.consoleView.component, BorderLayout.CENTER)
        }
    }

    fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
