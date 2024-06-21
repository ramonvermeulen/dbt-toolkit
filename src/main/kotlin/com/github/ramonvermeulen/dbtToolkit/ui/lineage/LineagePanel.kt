package com.github.ramonvermeulen.dbtToolkit.ui.lineage

import com.github.ramonvermeulen.dbtToolkit.JS_TRIGGERED_KEY
import com.github.ramonvermeulen.dbtToolkit.services.*
import com.github.ramonvermeulen.dbtToolkit.ui.IdeaPanel
import com.github.ramonvermeulen.dbtToolkit.ui.cef.CefLocalRequestHandler
import com.github.ramonvermeulen.dbtToolkit.ui.cef.CefStreamResourceHandler
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.removeUserData
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.jcef.*
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandlerAdapter
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities


class LineagePanel(private val project: Project, private val toolWindow: ToolWindow) : ActiveFileListener, IdeaPanel, Disposable {
    private val manifestService = project.service<ManifestService>()
    private val settings = project.service<DbtToolkitSettingsService>()
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
            javaScriptEngineProxy.addHandler(::handleJavaScriptCallback)
            setupJavascriptCallback()
            SwingUtilities.invokeLater {
                mainPanel.removeAll()
                mainPanel.add(browser.component, BorderLayout.CENTER)
                browser.loadURL("lineage-panel-dist/${settings.static.LINEAGE_PANEL_INDEX}")
            }
        }
    }

    private fun handleJavaScriptCallback(result: String): JBCefJSQuery.Response? {
        if (result == "refresh") {
            val file = FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
            if (file != null) {
                refreshLineageInfo(file)
            }
        } else {
            val path = "file://${settings.state.dbtProjectsDir}/${result}"
            val file = VirtualFileManager.getInstance().findFileByUrl(path)
            if (file !== null) {
                ApplicationManager.getApplication().invokeLater({
                    file.putUserData(JS_TRIGGERED_KEY, true)
                    FileEditorManager.getInstance(project).openFile(file, true)
                }, ModalityState.defaultModalityState())
            }
        }
        return null
    }

    private fun setupJavascriptCallback() {
        browser.jbCefClient.addLoadHandler(object : CefLoadHandlerAdapter() {
            override fun onLoadEnd(browser: CefBrowser?, frame: CefFrame?, httpStatusCode: Int) {
                browser?.executeJavaScript(
                    "window.selectNode = function(node) { ${javaScriptEngineProxy.inject("node")} };",
                    browser.url,
                    0
                )
            }
        }, browser.cefBrowser)
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

    private fun refreshLineageInfo(file: VirtualFile) {
        val newLineageInfo = getLineageInfo(file)
        // equals of LineageInfo is overwritten to skip node.isSelected in the check
        // basically only going into this block if it is a different lineage graph
        if (newLineageInfo != lineageInfo) {
            print("nieuwe graaf")
            SwingUtilities.invokeLater {
                if (lineageInfo != null) {
                    browser.cefBrowser.executeJavaScript("setLineageInfo(${lineageInfo!!.toJson()})", browser.cefBrowser.url, -1)
                }
            }
            lineageInfo = newLineageInfo
            return
        } else {
            val fileChangeIsJsTriggered = file.getUserData(JS_TRIGGERED_KEY)
            file.removeUserData(JS_TRIGGERED_KEY)

            if (fileChangeIsJsTriggered == true) {
                lineageInfo = newLineageInfo
                return
            }

            SwingUtilities.invokeLater {
                val oldActiveNode = lineageInfo?.nodes?.find { it.isSelected }
                val newActiveNode = newLineageInfo!!.nodes.find { it.isSelected }
                if (newActiveNode?.id != oldActiveNode?.id) {
                    browser.cefBrowser.executeJavaScript("setActiveNode('${newActiveNode?.id}')", browser.cefBrowser.url, -1)
                }
                lineageInfo = newLineageInfo
            }
        }
    }

    override fun activeFileChanged(file: VirtualFile?) {
        ApplicationManager.getApplication().executeOnPooledThread {
            if (file != null) {
                refreshLineageInfo(file)
            }
        }
    }

    private fun getLineageInfo(activeFile: VirtualFile?): LineageInfo? {
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
                val nodeId = "${nodeType.type}.$projectName.${file.nameWithoutExtension}"
                return manifestService.getLineageInfoForNode(nodeId)
            }
        }
        return lineageInfo
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {

    }
}
