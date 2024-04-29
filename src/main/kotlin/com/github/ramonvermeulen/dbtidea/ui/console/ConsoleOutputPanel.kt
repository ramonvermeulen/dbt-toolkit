package com.github.ramonvermeulen.dbtidea.ui.console

import com.github.ramonvermeulen.dbtidea.services.LoggingService
import com.github.ramonvermeulen.dbtidea.ui.APanel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class ConsoleOutputPanel(private val project: Project, private val toolWindow: ToolWindow) : APanel(project, toolWindow) {
    private val loggingService = project.service<LoggingService>()
    private val mainPanel: JPanel = JPanel(BorderLayout())

    override fun getContent(): JComponent {
        mainPanel.add(loggingService.consoleView.component, BorderLayout.CENTER)
        return mainPanel
    }
}
