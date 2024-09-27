package com.github.ramonvermeulen.dbtToolkit.ui.panels

import com.github.ramonvermeulen.dbtToolkit.JS_TRIGGERED_KEY
import com.github.ramonvermeulen.dbtToolkit.LINEAGE_PANEL_APP_DIR_NAME
import com.github.ramonvermeulen.dbtToolkit.LINEAGE_PANEL_CSS
import com.github.ramonvermeulen.dbtToolkit.LINEAGE_PANEL_INDEX
import com.github.ramonvermeulen.dbtToolkit.LINEAGE_PANEL_JS
import com.github.ramonvermeulen.dbtToolkit.SUPPORTED_LINEAGE_EXTENSIONS
import com.github.ramonvermeulen.dbtToolkit.models.LineageInfo
import com.github.ramonvermeulen.dbtToolkit.models.toJson
import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileListener
import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileService
import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.github.ramonvermeulen.dbtToolkit.services.LineageInfoListener
import com.github.ramonvermeulen.dbtToolkit.services.LineageInfoService
import com.github.ramonvermeulen.dbtToolkit.services.ManifestService
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
import com.intellij.ui.jcef.JBCefApp
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefBrowserBase
import com.intellij.ui.jcef.JBCefBrowserBuilder
import com.intellij.ui.jcef.JBCefJSQuery
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandlerAdapter
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities

class LineagePanel(private val project: Project) :
    ActiveFileListener, LineageInfoListener, IdeaPanel, Disposable {
    private val manifestService = project.service<ManifestService>()
    private val settings = project.service<DbtToolkitSettingsService>()
    private val ourCefClient = JBCefApp.getInstance().createClient()
    private val isDebug = System.getProperty("idea.plugin.in.sandbox.mode") == "true"
    private val browser: JBCefBrowser = JBCefBrowserBuilder().setClient(ourCefClient).setEnableOpenDevToolsMenuItem(isDebug).setOffScreenRendering(true).build()
    private val javaScriptEngineProxy: JBCefJSQuery = JBCefJSQuery.create(browser as JBCefBrowserBase)
    private val mainPanel = JPanel(BorderLayout())
    private var lineageInfo: LineageInfo? = null
    private var activeFile: VirtualFile? = null
    private var isRefreshingLineage = false
    private var completeLineage = false

    init {
        project.messageBus.connect().subscribe(ActiveFileService.TOPIC, this)
        project.messageBus.connect().subscribe(LineageInfoService.TOPIC, this)
        ApplicationManager.getApplication().executeOnPooledThread {
            initiateCefRequestHandler()
            SwingUtilities.invokeLater {
                mainPanel.add(JLabel("Loading..."), BorderLayout.CENTER)
            }
            javaScriptEngineProxy.addHandler(::handleJavaScriptCallback)
            setupJavascriptCallback()
            SwingUtilities.invokeLater {
                mainPanel.removeAll()
                mainPanel.add(browser.component, BorderLayout.CENTER)
                browser.loadURL("lineage-panel-dist/${LINEAGE_PANEL_INDEX}")
            }
        }
    }

    private fun handleJavaScriptCallback(result: String): JBCefJSQuery.Response? {
        val jsData = Json.parseToJsonElement(result)

        if (jsData.jsonObject.containsKey("refresh") || jsData.jsonObject.containsKey("showCompleteLineage")) {
            if (jsData.jsonObject.containsKey("showCompleteLineage")) {
                completeLineage = jsData.jsonObject["showCompleteLineage"]?.jsonPrimitive?.boolean ?: false
            }
            val file = FileEditorManager.getInstance(project).selectedFiles.firstOrNull()
            if (file != null && !isRefreshingLineage) {
                isRefreshingLineage = true
                ApplicationManager.getApplication().executeOnPooledThread {
                    try {
                        refreshLineageInfo(file, true, completeLineage)
                    } finally {
                        isRefreshingLineage = false
                    }
                }
            }
        } else {
            val path = "file://${settings.state.dbtProjectsDir}/$result"
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
        browser.jbCefClient.addLoadHandler(
            object : CefLoadHandlerAdapter() {
                override fun onLoadEnd(
                    browser: CefBrowser?,
                    frame: CefFrame?,
                    httpStatusCode: Int,
                ) {
                    browser?.executeJavaScript(
                        "window.kotlinCallback = function(value) { ${javaScriptEngineProxy.inject("value")} };",
                        browser.url,
                        0,
                    )
                }
            },
            browser.cefBrowser,
        )
    }

    private fun initiateCefRequestHandler() {
        val myRequestHandler = CefLocalRequestHandler()
        myRequestHandler.addResource(LINEAGE_PANEL_INDEX) {
            javaClass.classLoader.getResourceAsStream("${LINEAGE_PANEL_APP_DIR_NAME}/${LINEAGE_PANEL_INDEX}")?.let {
                CefStreamResourceHandler(it, "text/html", this@LineagePanel)
            }
        }
        myRequestHandler.addResource(LINEAGE_PANEL_JS) {
            javaClass.classLoader.getResourceAsStream("${LINEAGE_PANEL_APP_DIR_NAME}/${LINEAGE_PANEL_JS}")?.let {
                CefStreamResourceHandler(it, "text/javascript", this)
            }
        }
        myRequestHandler.addResource(LINEAGE_PANEL_CSS) {
            javaClass.classLoader.getResourceAsStream("${LINEAGE_PANEL_APP_DIR_NAME}/${LINEAGE_PANEL_CSS}")?.let {
                CefStreamResourceHandler(it, "text/css", this)
            }
        }
        ourCefClient.addRequestHandler(myRequestHandler, browser.cefBrowser)
    }

    private fun handleSameNodesAndEdges(
        file: VirtualFile,
        newLineageInfo: LineageInfo?,
    ) {
        val fileChangeIsJsTriggered = file.getUserData(JS_TRIGGERED_KEY)
        file.removeUserData(JS_TRIGGERED_KEY)

        if (fileChangeIsJsTriggered == true) {
            // if it is JS triggered, the UI is already updated, so we only have to set the new state
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

    // subscribers
    override fun activeFileChanged(file: VirtualFile?) {
        ApplicationManager.getApplication().executeOnPooledThread {
            if (file != null && SUPPORTED_LINEAGE_EXTENSIONS.contains(file.extension)) {
                activeFile = file
                refreshLineageInfo(file, false, completeLineage)
            }
        }
    }

    override fun lineageInfoChanged(newLineageInfo: LineageInfo) {
        ApplicationManager.getApplication().executeOnPooledThread {
            if (newLineageInfo == lineageInfo && activeFile!!.exists()) {
                handleSameNodesAndEdges(activeFile!!, newLineageInfo)
                return@executeOnPooledThread
            }

            SwingUtilities.invokeLater {
                lineageInfo = newLineageInfo
                lineageInfo?.let {
                    browser.cefBrowser.executeJavaScript("setLineageInfo(${it.toJson()})", browser.cefBrowser.url, -1)
                }
            }
        }
    }

    private fun refreshLineageInfo(
        activeFile: VirtualFile?,
        enforceReparse: Boolean,
        completeLineage: Boolean,
    ) {
        val projectName = settings.state.dbtProjectName

        data class NodeType(val pattern: Regex, val type: String)

        val nodeTypes =
            listOf(
                NodeType(
                    Regex(settings.state.dbtModelPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }),
                    "model",
                ),
                NodeType(
                    Regex(settings.state.dbtTestPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }),
                    "test",
                ),
                NodeType(
                    Regex(settings.state.dbtSeedPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }),
                    "seed",
                ),
                NodeType(
                    Regex(settings.state.dbtMacroPaths.joinToString("|", prefix = ".*/(", postfix = ")/.*") { Regex.escape(it) }),
                    "macro",
                ),
            )

        activeFile?.let { file ->
            nodeTypes.firstOrNull { file.path.matches(it.pattern) }?.let { nodeType ->
                val nodeId = "${nodeType.type}.$projectName.${file.nameWithoutExtension}"
                manifestService.refreshLineageInfoForNode(nodeId, enforceReparse, completeLineage)
            }
        }
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {
    }
}
