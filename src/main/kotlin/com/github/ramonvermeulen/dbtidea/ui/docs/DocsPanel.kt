package com.github.ramonvermeulen.dbtidea.ui.docs

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
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
import org.apache.commons.io.IOUtils
import java.awt.BorderLayout
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class DocsPanel(private var project: Project, private var toolWindow: ToolWindow) : Disposable {
    private val docsService = project.service<DocsService>()
    private val settings = project.service<DbtIdeaSettingsService>()
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
                    ApplicationManager.getApplication().executeOnPooledThread {
                        showLoadingIndicator("Executing dbt docs generate...") {
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
            showLoadingIndicator("Executing dbt docs generate...") {
                val myRequestHandler = CefLocalRequestHandler()
                val docs = docsService.getDocs()
                val manifest = File("${docs.parent}/manifest.json")
                val catalog = File("${docs.parent}/catalog.json")
                if (docs.exists() && manifest.exists() && catalog.exists()) {
                    myRequestHandler.addResource("index.html") {
                        docs.bufferedReader().use {
                            val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                            CefStreamResourceHandler(inputStream, "text/html", this@DocsPanel)
                        }
                    }
                    myRequestHandler.addResource("manifest.json") {
                        manifest.bufferedReader().use {
                            val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                            CefStreamResourceHandler(inputStream, "text/html", this)
                        }
                    }
                    myRequestHandler.addResource("catalog.json") {
                        catalog.bufferedReader().use {
                            val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                            CefStreamResourceHandler(inputStream, "text/html", this)
                        }
                    }
                    ourCefClient.addRequestHandler(myRequestHandler, browser.cefBrowser)
                }
            }

            SwingUtilities.invokeLater {
                mainPanel.add(regenerateButton, BorderLayout.NORTH)
                mainPanel.add(browser.component, BorderLayout.CENTER)
                browser.loadURL(settings.state.dbtTargetDir + "/index.html")
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
        val backgroundTask =
            object : Task.Backgroundable(project, title, false) {
                override fun run(indicator: ProgressIndicator) {
                    indicator.isIndeterminate = true
                    task.invoke()
                }
            }
        ProgressManager.getInstance().run(backgroundTask)
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
