package com.github.ramonvermeulen.dbtidea.ui.lineage

import com.github.ramonvermeulen.dbtidea.services.*
import com.github.ramonvermeulen.dbtidea.ui.IdeaPanel
import com.github.ramonvermeulen.dbtidea.ui.cef.CefLocalRequestHandler
import com.github.ramonvermeulen.dbtidea.ui.cef.CefStreamResourceHandler
import com.ibm.icu.text.SimpleDateFormat
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
            initiateCefRequestHandler()
            SwingUtilities.invokeLater{
                mainPanel.add(JLabel("Loading..."), BorderLayout.CENTER)
            }
            javaScriptEngineProxy.addHandler { result ->
                println(result)
                null
            }
            //val htmlContent = getHtmlContent()
            SwingUtilities.invokeLater {
                mainPanel.removeAll()
                mainPanel.add(browser.component, BorderLayout.CENTER)
                browser.loadURL("lineage-panel-dist/${settings.static.LINEAGE_PANEL_INDEX}")
            }
        }
    }

    private fun initiateCefRequestHandler() {
        val myRequestHandler = CefLocalRequestHandler()
        myRequestHandler.addResource(settings.static.LINEAGE_PANEL_INDEX) {
            javaClass.classLoader.getResourceAsStream("lineage-panel-dist/${settings.static.LINEAGE_PANEL_INDEX}")?.let {
                CefStreamResourceHandler(it, "text/html", this@LineagePanel)
            }
        }
        myRequestHandler.addResource(settings.static.LINEAGE_PANEL_JS) {
            javaClass.classLoader.getResourceAsStream("lineage-panel-dist/${settings.static.LINEAGE_PANEL_JS}")?.let {
                CefStreamResourceHandler(it, "text/javascript", this)
            }
        }
        myRequestHandler.addResource(settings.static.LINEAGE_PANEL_CSS) {
            javaClass.classLoader.getResourceAsStream("lineage-panel-dist/${settings.static.LINEAGE_PANEL_CSS}")?.let {
                CefStreamResourceHandler(it, "text/css", this)
            }
        }
        ourCefClient.addRequestHandler(myRequestHandler, browser.cefBrowser)
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
                    ${lineageInfo?.toJson()?.toString()}
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
            //SwingUtilities.invokeLater {
            //    browser.loadHTML(htmlContent)
            //}
        }
    }

    private fun getLineageInfo(activeFile: VirtualFile?) {
        val projectName = settings.state.dbtProjectName
        data class NodeType(val pattern: Regex, val type: String)

        val nodeTypes = listOf(
            NodeType(Regex(settings.state.dbtModelPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }), "model"),
            NodeType(Regex(settings.state.dbtTestPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }), "test"),
            NodeType(Regex(settings.state.dbtSeedPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }), "seed"),
            NodeType(Regex(settings.state.dbtMacroPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }), "macro")
        )

        activeFile?.let { file ->
            nodeTypes.firstOrNull { file.path.matches(it.pattern) }?.let { nodeType ->
                lineageInfo = manifestService.getLineageInfoForNode("${nodeType.type}.$projectName.${file.nameWithoutExtension}")
            }
        }
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {

    }
}
