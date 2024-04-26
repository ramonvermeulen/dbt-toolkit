package com.github.ramonvermeulen.dbtidea.ui

import com.github.ramonvermeulen.dbtidea.services.DocsService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.JBCefBrowserBuilder
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class DocsPanel(private val project: Project, private val toolWindow: ToolWindow) {
    private val docsService = project.service<DocsService>()

    fun getContent(): JComponent {
        val panel = JPanel(BorderLayout())
        val browser = JBCefBrowserBuilder().setUrl("about:blank").build()
        val regenButton = JButton("Regenerate Docs")
        regenButton.addActionListener {
            SwingUtilities.invokeLater {
                val docs = docsService.getDocs()
                browser.loadHTML(docs.readText())
            }
        }
        panel.add(regenButton, BorderLayout.BEFORE_FIRST_LINE)
        panel.add(browser.component, BorderLayout.CENTER)

        SwingUtilities.invokeLater {
            val docs = docsService.getDocs()
            browser.loadHTML(docs.readText())
        }
        return panel
    }
}
