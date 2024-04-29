package com.github.ramonvermeulen.dbtidea.ui.lineage

import com.github.ramonvermeulen.dbtidea.services.ManifestService
import com.github.ramonvermeulen.dbtidea.ui.APanel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.JBCefBrowserBase
import com.intellij.ui.jcef.JBCefBrowserBuilder
import com.intellij.ui.jcef.JBCefJSQuery
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class LineagePanel(private val project: Project, private val toolWindow: ToolWindow) : APanel(project, toolWindow) {
    private val manifestService = project.service<ManifestService>()

    override fun getContent(): JComponent {
        val panel = JPanel(BorderLayout())
        val browser = JBCefBrowserBuilder().setUrl("about:blank").build()
        val javaScriptEngineProxy: JBCefJSQuery = JBCefJSQuery.create(browser as JBCefBrowserBase)

        javaScriptEngineProxy.addHandler { result ->
            println(result)
            null
        }

        panel.add(browser.component, BorderLayout.CENTER)

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
            </body>
            </html>
            """.trimIndent()

        SwingUtilities.invokeLater {
            browser.loadHTML(htmlContent)
        }
        return panel
    }
}
