package com.github.ramonvermeulen.dbtToolkit.ui.docs

import com.github.ramonvermeulen.dbtToolkit.DBT_CATALOG_FILE
import com.github.ramonvermeulen.dbtToolkit.DBT_DOCS_FILE
import com.github.ramonvermeulen.dbtToolkit.DBT_MANIFEST_FILE
import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.github.ramonvermeulen.dbtToolkit.services.DocsService
import com.github.ramonvermeulen.dbtToolkit.ui.IdeaPanel
import com.github.ramonvermeulen.dbtToolkit.ui.cef.CefLocalRequestHandler
import com.github.ramonvermeulen.dbtToolkit.ui.cef.CefStreamResourceHandler
import com.ibm.icu.text.SimpleDateFormat
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.ui.jcef.JBCefApp
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefBrowserBuilder
import org.apache.commons.io.IOUtils
import java.awt.FlowLayout
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities

class DocsPanel(project: Project) : IdeaPanel, Disposable {
    private val docsService = project.service<DocsService>()
    private val settings = project.service<DbtToolkitSettingsService>()
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
                        try {
                            val docs = docsService.getDocs(forceRegen = true)
                            docs.lastModified().let {
                                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                lastModifiedLabel.text = "Last modified: ${sdf.format(it)}"
                            }
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
    private val lastModifiedLabel = JLabel()

    init {
        val executor = Executors.newSingleThreadExecutor()
        val future =
            CompletableFuture.runAsync(
                {
                    initiateCefRequestHandler()
                },
                executor,
            )

        future.thenRun {
            SwingUtilities.invokeLater {
                regenerateButton.isEnabled = true
                regenerateButton.text = "Regenerate Docs"
                browser.loadURL("${settings.state.settingsDbtTargetDir}/${DBT_DOCS_FILE}")
            }
        }.exceptionally { throwable ->
            throwable.printStackTrace()
            null
        }

        SwingUtilities.invokeLater {
            mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)
            regenerateButton.isEnabled = false
            regenerateButton.text = "Loading..."
            buttonPanel.add(regenerateButton)
            buttonPanel.add(lastModifiedLabel)
            mainPanel.add(buttonPanel)
            mainPanel.add(browser.component)
        }
    }

    private fun initiateCefRequestHandler() {
        val myRequestHandler = CefLocalRequestHandler()
        val docs = docsService.getDocs()
        docs.lastModified().let {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            lastModifiedLabel.text = "Last modified: ${sdf.format(it)}"
        }
        val manifest = File("${settings.state.settingsDbtTargetDir}/${DBT_MANIFEST_FILE}")
        val catalog = File("${settings.state.settingsDbtTargetDir}/${DBT_CATALOG_FILE}")
        if (docs.exists() && manifest.exists() && catalog.exists()) {
            myRequestHandler.addResource(DBT_DOCS_FILE) {
                docs.bufferedReader().use {
                    val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                    CefStreamResourceHandler(inputStream, "text/html", this@DocsPanel)
                }
            }
            myRequestHandler.addResource(DBT_MANIFEST_FILE) {
                manifest.bufferedReader().use {
                    val inputStream = IOUtils.toInputStream(IOUtils.toString(it), StandardCharsets.UTF_8)
                    CefStreamResourceHandler(inputStream, "application/json", this)
                }
            }
            myRequestHandler.addResource(DBT_CATALOG_FILE) {
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

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
