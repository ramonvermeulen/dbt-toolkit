package com.github.ramonvermeulen.dbtidea.ui

import com.github.ramonvermeulen.dbtidea.services.ManifestService
import com.intellij.openapi.components.service
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.JBCefBrowserBuilder
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class LineagePanel (private val project: Project, private val toolWindow: ToolWindow) {
    private val manifestService = project.service<ManifestService>()

    fun getContent(): JComponent {
        val panel = JPanel(BorderLayout())
        val browser = JBCefBrowserBuilder().setUrl("about:blank").build()
        panel.add(browser.component, BorderLayout.CENTER)

        // JavaScript code to handle button click and send data to Kotlin
        val javascriptCode = """
            function sendDataToKotlin() {
                // Example data to send to Kotlin
                var data = {
                    message: "Hello from JavaScript!"
                };
                
                // Send data to Kotlin
                window.bridge.receiveDataFromJavaScript(JSON.stringify(data));
            }
        """.trimIndent()

        // Load HTML content with button and JavaScript code
        val htmlContent = """
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

        SwingUtilities.invokeLater{
            browser.loadHTML(htmlContent)
        }
        return panel
    }
}