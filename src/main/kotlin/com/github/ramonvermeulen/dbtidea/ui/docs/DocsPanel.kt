package com.github.ramonvermeulen.dbtidea.ui.docs

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
import com.github.ramonvermeulen.dbtidea.services.DocsService
import com.github.ramonvermeulen.dbtidea.ui.IdeaPanel
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
import java.awt.FlowLayout
import java.io.File
import java.nio.charset.StandardCharsets
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class DocsPanel(private var project: Project, private var toolWindow: ToolWindow) : IdeaPanel, Disposable {
    private val docsService = project.service<DocsService>()
    private val settings = project.service<DbtIdeaSettingsService>()
    private val ourCefClient = JBCefApp.getInstance().createClient()
    private val isDebug = System.getProperty("idea.plugin.in.sandbox.mode") == "true"
    private val browser: JBCefBrowser = JBCefBrowserBuilder().setClient(ourCefClient).setEnableOpenDevToolsMenuItem(isDebug).build()
    private val mainPanel: JPanel = JPanel()
    private val buttonPanel = JPanel(FlowLayout(FlowLayout.LEFT))

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
                                print(docs.absolutePath + "\n")
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
                initiateCefRequestHandler()
                SwingUtilities.invokeLater {
                    regenerateButton.isEnabled = true
                    regenerateButton.text = "Regenerate Docs"
                    browser.loadURL("${settings.state.dbtTargetDir}/${settings.static.DBT_DOCS_FILE}")
                }
            }
            SwingUtilities.invokeLater {
                mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)
                regenerateButton.isEnabled = false
                regenerateButton.text = "Loading..."
                buttonPanel.add(regenerateButton)
                mainPanel.add(buttonPanel)
                mainPanel.add(browser.component)
            }
            Disposer.register(ApplicationManager.getApplication(), ourCefClient)
        }
    }

    private fun initiateCefRequestHandler() {
        val myRequestHandler = CefLocalRequestHandler()
        val docs = docsService.getDocs()
        val manifest = File("${settings.state.dbtTargetDir}/${settings.static.DBT_MANIFEST_FILE}")
        val catalog = File("${settings.state.dbtTargetDir}/${settings.static.DBT_CATALOG_FILE}")
        if (docs.exists() && manifest.exists() && catalog.exists()) {
            myRequestHandler.addResource(settings.static.DBT_DOCS_FILE) {
                docs.bufferedReader().use {
                    val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                    CefStreamResourceHandler(inputStream, "text/html", this@DocsPanel)
                }
            }
            myRequestHandler.addResource(settings.static.DBT_MANIFEST_FILE) {
                manifest.bufferedReader().use {
                    val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                    CefStreamResourceHandler(inputStream, "application/json", this)
                }
            }
            myRequestHandler.addResource(settings.static.DBT_CATALOG_FILE) {
                catalog.bufferedReader().use {
                    val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                    CefStreamResourceHandler(inputStream, "application/json", this)
                }
            }
            ourCefClient.addRequestHandler(myRequestHandler, browser.cefBrowser)
        }
    }

    override fun getContent(): JComponent {
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
