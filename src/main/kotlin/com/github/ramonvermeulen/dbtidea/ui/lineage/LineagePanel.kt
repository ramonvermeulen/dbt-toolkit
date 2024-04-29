package com.github.ramonvermeulen.dbtidea.ui.lineage

import com.github.ramonvermeulen.dbtidea.services.ActiveFileService
import com.github.ramonvermeulen.dbtidea.services.LineageInfo
import com.github.ramonvermeulen.dbtidea.services.ManifestService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.*
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class LineagePanel(private val project: Project, private val toolWindow: ToolWindow) {
    private val manifestService = project.service<ManifestService>()
    private val activeFileService = project.service<ActiveFileService>()
    private val ourCefClient = JBCefApp.getInstance().createClient()
    private val isDebug = System.getProperty("idea.plugin.in.sandbox.mode") == "true"
    private val browser: JBCefBrowser = JBCefBrowserBuilder().setClient(ourCefClient).setEnableOpenDevToolsMenuItem(isDebug).build()
    private val javaScriptEngineProxy: JBCefJSQuery = JBCefJSQuery.create(browser as JBCefBrowserBase)
    private val mainPanel = JPanel(BorderLayout())
    private var lineageInfo: LineageInfo? = null

    init {
        javaScriptEngineProxy.addHandler { result ->
            println(result)
            null
        }
        SwingUtilities.invokeLater {
            mainPanel.add(browser.component, BorderLayout.CENTER)
            val javascriptCode =
                """
            function sendDataToKotlin() {
                // Example data to send to Kotlin
                var data = {
                    message: "Hello from JavaScript!"
                };
                
                ${javaScriptEngineProxy.inject("data")}
            }
            """.trimIndent()

            val htmlContent =
                """
            <html>
            <head>
                <script>
                    $javascriptCode
                </script>
            </head>
            <body>
                <button onclick="sendDataToKotlin()">Click me!</button>
                <table>
                    <tr>
                        <td>Active Node</td>
                        <td>${lineageInfo?.node}</td>
                    </tr>
                    <tr>
                        <td>children</td>
                        <td>${lineageInfo?.children.toString()}</td>
                    </tr>
                    <tr>
                        <td>parents</td>
                        <td>${lineageInfo?.parents.toString()}</td>
                    </tr>
                </table>
            </body>
            </html>
            """.trimIndent()
            browser.loadHTML(htmlContent)
        }
    }

    fun getLineageInfo() {
        val activeFile = activeFileService.getActiveFile()
        if (activeFile != null) {
            lineageInfo = manifestService.getLineageInfoForNode("model.dbt_training.${activeFile.name.split(".").first()}")
        }
    }

    fun getContent(): JComponent {
        return mainPanel
    }
}
