package com.github.ramonvermeulen.dbtidea.ui.docs

import com.github.ramonvermeulen.dbtidea.services.DocsService
import com.github.ramonvermeulen.dbtidea.ui.cef.CefLocalRequestHandler
import com.github.ramonvermeulen.dbtidea.ui.cef.CefStreamResourceHandler
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.JBCefApp
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefBrowserBuilder
import java.awt.BorderLayout
import java.io.File
import java.io.FileInputStream
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class DocsPanel(private var project: Project, private var toolWindow: ToolWindow) : Disposable {
    private val docsService = project.service<DocsService>()
    private val ourCefClient = JBCefApp.getInstance().createClient()
    private val isDebug = System.getProperty("idea.plugin.in.sandbox.mode") == "true"
    private val browser: JBCefBrowser = JBCefBrowserBuilder().setClient(ourCefClient).setEnableOpenDevToolsMenuItem(isDebug).build()
    private val mainPanel: JPanel = JPanel(BorderLayout())
    private val regenerateButton =
        object : JButton("Regenerate Docs") {
            init {
                addActionListener {
                    SwingUtilities.invokeLater {
                        isEnabled = false
                        text = "Loading..."
                    }
                    showLoadingIndicator("Executing dbt docs generate...") {
                        ApplicationManager.getApplication().executeOnPooledThread {
                            try {
                                val docs = docsService.getDocs()
                                browser.loadURL(docs.absolutePath)
                            } finally {
                                SwingUtilities.invokeLater {
                                    isEnabled = true
                                    text = "Regenerate Docs"
                                }
                            }
                        }
                    }
                }
            }
        }

    init {
        ApplicationManager.getApplication().executeOnPooledThread {
            val myRequestHandler = CefLocalRequestHandler()
            val docs = docsService.getDocs()

            myRequestHandler.addResource("index.html") {
                CefStreamResourceHandler(FileInputStream(docs), "text/html", this@DocsPanel)
            }
            myRequestHandler.addResource("manifest.json") {
                CefStreamResourceHandler(FileInputStream(File("${docs.parent}/manifest.json")), "application/json", this@DocsPanel)
            }
            myRequestHandler.addResource("catalog.json") {
                CefStreamResourceHandler(FileInputStream(File("${docs.parent}/catalog.json")), "application/json", this@DocsPanel)
            }
            ourCefClient.addRequestHandler(myRequestHandler, browser.cefBrowser)

            SwingUtilities.invokeLater {
                mainPanel.add(regenerateButton, BorderLayout.NORTH)
                mainPanel.add(browser.component, BorderLayout.CENTER)
                browser.loadURL(docs.absolutePath)
            }
            Disposer.register(ApplicationManager.getApplication(), ourCefClient)
        }
    }

    fun getContent(): JComponent {
        return mainPanel
    }

    fun showLoadingIndicator(
        title: String,
        task: () -> Unit,
    ) {
        val dbtParseTask =
            object : Task.Backgroundable(project, title, false) {
                override fun run(indicator: ProgressIndicator) {
                    indicator.isIndeterminate = true
                    task.invoke()
                }
            }
        ProgressManager.getInstance().run(dbtParseTask)
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
