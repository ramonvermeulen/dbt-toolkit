package com.github.ramonvermeulen.dbtidea.ui.lineage

import com.github.ramonvermeulen.dbtidea.services.*
import com.github.ramonvermeulen.dbtidea.ui.IdeaPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.*
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities

class LineagePanel(private val project: Project, private val toolWindow: ToolWindow) : ActiveFileListener, IdeaPanel, Disposable {
    private val manifestService = project.service<ManifestService>()
    private val settings = project.service<DbtIdeaSettingsService>()
    private val ourCefClient = JBCefApp.getInstance().createClient()
    private val isDebug = System.getProperty("idea.plugin.in.sandbox.mode") == "true"
    private val browser: JBCefBrowser = JBCefBrowserBuilder().setClient(ourCefClient).setEnableOpenDevToolsMenuItem(isDebug).build()
    private val javaScriptEngineProxy: JBCefJSQuery = JBCefJSQuery.create(browser as JBCefBrowserBase)
    private val mainPanel = JPanel(BorderLayout())
    private var lineageInfo: LineageInfo? = null

    init {
        project.messageBus.connect().subscribe(ActiveFileService.TOPIC, this)
        ApplicationManager.getApplication().executeOnPooledThread {
            SwingUtilities.invokeLater{
                mainPanel.add(JLabel("Loading..."), BorderLayout.CENTER)
            }
            javaScriptEngineProxy.addHandler { result ->
                println(result)
                null
            }
            val htmlContent = getHtmlContent()
            SwingUtilities.invokeLater {
                mainPanel.removeAll()
                mainPanel.add(browser.component, BorderLayout.CENTER)
                browser.loadHTML(htmlContent)
            }
        }
    }

    private fun getHtmlContent(): String {
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
                            <td><b>current node:</b></td>
                            <td>${lineageInfo?.node}</td>
                        </tr>
                        <tr>
                            <td><b>children:</b></td>
                            <td>${lineageInfo?.children.toString()}</td>
                        </tr>
                        <tr>
                            <td><b>parents:</b></td>
                            <td>${lineageInfo?.parents.toString()}</td>
                        </tr>
                    </table>
                </body>
            </html>
            """.trimIndent()

        return htmlContent
    }

    override fun activeFileChanged(file: VirtualFile?) {
        ApplicationManager.getApplication().executeOnPooledThread {
            getLineageInfo(file)
            val htmlContent = getHtmlContent()
            SwingUtilities.invokeLater {
                browser.loadHTML(htmlContent)
            }
        }
    }

    private fun getLineageInfo(activeFile: VirtualFile?) {
        val projectName = settings.state.dbtProjectName
        val modelsPattern = Regex(settings.state.dbtModelPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) })
        val testsPattern = Regex(settings.state.dbtTestPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) })
        val seedsPattern = Regex(settings.state.dbtSeedPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) })
        val macrosPattern = Regex(settings.state.dbtMacroPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) })
        if (activeFile != null) {
            if (activeFile.path.matches(modelsPattern)) {
                lineageInfo = manifestService.getLineageInfoForNode("model.${projectName}.${activeFile.name.split(".").first()}")
            } else if (activeFile.path.matches(testsPattern)) {
                lineageInfo = manifestService.getLineageInfoForNode("test.${projectName}.${activeFile.name.split(".").first()}")
            } else if (activeFile.path.matches(seedsPattern)) {
                lineageInfo = manifestService.getLineageInfoForNode("seed.${projectName}.${activeFile.name.split(".").first()}")
            } else if (activeFile.path.matches(macrosPattern)) {
                lineageInfo = manifestService.getLineageInfoForNode("macro.${projectName}.${activeFile.name.split(".").first()}")
            }
        }
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {

    }
}
