package com.github.ramonvermeulen.dbtidea.ui

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
import com.github.ramonvermeulen.dbtidea.services.ManifestService
import com.github.ramonvermeulen.dbtidea.ui.console.ConsoleOutputPanel
import com.github.ramonvermeulen.dbtidea.ui.docs.DocsPanel
import com.github.ramonvermeulen.dbtidea.ui.lineage.LineagePanel
import com.intellij.openapi.components.service
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vcs.changes.VcsDirtyScopeManager
import com.intellij.openapi.vcs.changes.VcsIgnoreManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileVisitor
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.ContentManagerEvent
import com.intellij.ui.content.ContentManagerListener
import javax.swing.JPanel

class DbtIdeaMainToolWindow : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow,
    ) {
        val contentFactory = ContentFactory.getInstance()
        val panels = listOf(
            LineagePanel(project, toolWindow) to "dbt lineage",
            DocsPanel(project, toolWindow) to "dbt docs",
            ConsoleOutputPanel(project, toolWindow) to "console (read-only)"
        )

        panels.forEach { (panel, title) ->
            toolWindow.contentManager.addContent(
                contentFactory.createContent(panel.getContent(), title, false),
            )
        }

        toolWindow.contentManager.addContentManagerListener(
            object : ContentManagerListener {
                override fun selectionChanged(event: ContentManagerEvent) {
                    handleTabChange(project, event)
                }
            },
        )
    }

    override fun shouldBeAvailable(project: Project) = true

    override fun isApplicable(project: Project): Boolean {
        val changeListManager = ChangeListManager.getInstance(project)
        var isDbtProject = false

        project.guessProjectDir()?.let {
            VfsUtil.visitChildrenRecursively(it, object: VirtualFileVisitor<Any>() {
                override fun visitFile(file: VirtualFile): Boolean {
                    if (changeListManager.isIgnoredFile(file) || file.path.matches(Regex(".*/(target|dbt_packages)/.*"))) {
                        return false
                    }
                    if (file.name == "dbt_project.yml") {
                        project.service<DbtIdeaSettingsService>().parseDbtProjectFile(file)
                        isDbtProject = true
                        return false
                    }
                    return true
                }
            })
        }
        return isDbtProject
    }

    private fun handleTabChange(
        project: Project,
        event: ContentManagerEvent,
    ) {
        // handle tab change impl
    }
}
