package com.github.ramonvermeulen.dbtToolkit.ui

import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.github.ramonvermeulen.dbtToolkit.ui.console.ConsoleOutputPanel
import com.github.ramonvermeulen.dbtToolkit.ui.docs.DocsPanel
import com.github.ramonvermeulen.dbtToolkit.ui.lineage.LineagePanel
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileVisitor
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.ContentManagerEvent
import com.intellij.ui.content.ContentManagerListener
import java.util.function.Supplier
import javax.swing.JPanel

class DbtToolkitMainToolWindow : ToolWindowFactory, DumbAware {
    data class PanelInfo(val creator: Supplier<IdeaPanel>, val isLazy: Boolean)
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow,
    ) {
        val contentFactory = ContentFactory.getInstance()

        val panelCreators = mapOf(
            "dbt lineage" to PanelInfo(Supplier { LineagePanel(project, toolWindow) }, false),
            "dbt docs" to PanelInfo(Supplier { DocsPanel(project, toolWindow) }, true),
            "console (read-only)" to PanelInfo(Supplier { ConsoleOutputPanel(project, toolWindow) }, false),
        )

        val panels = mutableMapOf<String, IdeaPanel>()

        panelCreators.forEach { (title, panelInfo) ->
            if (!panelInfo.isLazy) {
                val panel = panelInfo.creator.get()
                panels[title] = panel
                val content = contentFactory.createContent(panel.getContent(), title, false)
                toolWindow.contentManager.addContent(content)
            } else {
                val dummyPanel = JPanel()
                val content = contentFactory.createContent(dummyPanel, title, false)
                toolWindow.contentManager.addContent(content)
            }
        }

        toolWindow.contentManager.addContentManagerListener(
            object : ContentManagerListener {
                override fun selectionChanged(event: ContentManagerEvent) {
                    val selectedContent = event.content
                    val selectedTitle = selectedContent.tabName

                    if (panels[selectedTitle] == null && panelCreators[selectedTitle]?.isLazy == true) {
                        val panel = panelCreators[selectedTitle]?.creator?.get()
                        if (panel != null) {
                            panels[selectedTitle] = panel
                            selectedContent.component = panel.getContent()
                        }
                    }
                }
            },
        )
    }

    override fun shouldBeAvailable(project: Project) = true

    override suspend fun isApplicableAsync(project: Project): Boolean {
        val changeListManager = ChangeListManager.getInstance(project)
        var isDbtProject = false

        project.guessProjectDir()?.let {
            VfsUtil.visitChildrenRecursively(it, object : VirtualFileVisitor<Any>() {
                override fun visitFile(file: VirtualFile): Boolean {
                    if (changeListManager.isIgnoredFile(file) || file.path.matches(Regex(".*/(target|dbt_packages)/.*"))) {
                        return false
                    }
                    if (file.name == "dbt_project.yml") {
                        project.service<DbtToolkitSettingsService>().parseDbtProjectFile(file)
                        isDbtProject = true
                        return false
                    }
                    return true
                }
            })
        }
        return isDbtProject
    }
}
