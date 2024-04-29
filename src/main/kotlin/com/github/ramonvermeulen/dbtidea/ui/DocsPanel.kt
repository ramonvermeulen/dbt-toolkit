package com.github.ramonvermeulen.dbtidea.ui

import com.github.ramonvermeulen.dbtidea.services.DocsService
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.JBCefApp
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefBrowserBuilder
import org.intellij.images.editor.impl.jcef.CefLocalRequestHandler
import org.intellij.images.editor.impl.jcef.CefStreamResourceHandler
import java.awt.BorderLayout
import java.io.FileInputStream
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class DocsPanel(private val project: Project, private val toolWindow: ToolWindow) : Disposable {
    private val docsService = project.service<DocsService>()
    private val ourCefClient = JBCefApp.getInstance().createClient()
    // replace with isDebug()
    private val isDebug = System.getProperty("java.vm.debug") != null
    private val browser: JBCefBrowser = JBCefBrowserBuilder().setClient(ourCefClient).setEnableOpenDevToolsMenuItem(true).build()
    private val panel: JPanel = JPanel(BorderLayout())

    init {
        Disposer.register(ApplicationManager.getApplication(), ourCefClient)
    }


    fun getContent(): JComponent {
        val regenButton = JButton("Regenerate Docs")
        regenButton.addActionListener {
            SwingUtilities.invokeLater {
                val docs = docsService.getDocs()
                browser.loadHTML(docs.readText())
            }
        }
        panel.add(regenButton, BorderLayout.BEFORE_FIRST_LINE)

        val myRequestHandler = CefLocalRequestHandler("http", "localhost")
        val docs = docsService.getDocs()

        myRequestHandler.addResource(docs.absolutePath) {
            CefStreamResourceHandler(FileInputStream(docs), "text/html", this@DocsPanel)
        }
        myRequestHandler.addResource(docs.parent + "/manifest.json") {
            javaClass.getResourceAsStream(docs.absolutePath + "/manifest.json")?.let {
                CefStreamResourceHandler(it, "application/json", this)
            }
        }
        myRequestHandler.addResource(docs.parent + "/catalog.json") {
            javaClass.getResourceAsStream(docs.absolutePath + "/catalog.json")?.let {
                CefStreamResourceHandler(it, "application/json", this)
            }
        }

        ourCefClient.addRequestHandler(myRequestHandler, browser.cefBrowser)

        panel.add(browser.component, BorderLayout.CENTER)
        SwingUtilities.invokeLater {
            browser.loadURL(docs.absolutePath)
        }
        return panel
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
