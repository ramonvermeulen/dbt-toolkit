package com.github.ramonvermeulen.dbtToolkit.ui.console

import com.github.ramonvermeulen.dbtToolkit.services.LoggingEvent
import com.github.ramonvermeulen.dbtToolkit.services.LoggingListener
import com.github.ramonvermeulen.dbtToolkit.services.LoggingService
import com.github.ramonvermeulen.dbtToolkit.ui.IdeaPanel
import com.intellij.execution.impl.ConsoleViewImpl
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class ConsoleOutputPanel(private val project: Project, private val toolWindow: ToolWindow) : IdeaPanel, Disposable, LoggingListener {
    private val mainPanel: JPanel = JPanel(BorderLayout())
    private val consoleView: ConsoleView = ConsoleViewImpl(project, false)

    init {
        project.messageBus.connect().subscribe(LoggingService.TOPIC, this)
        SwingUtilities.invokeLater {
            mainPanel.add(consoleView.component, BorderLayout.CENTER)
        }
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun logEvent(event: LoggingEvent) {
        SwingUtilities.invokeLater {
            consoleView.print(event.message, event.type)
        }
    }

    override fun flush() {
        SwingUtilities.invokeLater {
            consoleView.clear()
        }
    }

    override fun dispose() {
        SwingUtilities.invokeLater {
            consoleView.dispose()
        }
    }
}
