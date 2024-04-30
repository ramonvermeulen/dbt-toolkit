package com.github.ramonvermeulen.dbtidea.ui

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
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.ContentManagerEvent
import com.intellij.ui.content.ContentManagerListener

class DbtIdeaMainToolWindow : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow,
    ) {
        val contentFactory = ContentFactory.getInstance()
        val lineagePanel = LineagePanel(project, toolWindow)
        val docsPanel = DocsPanel(project, toolWindow)
        val consolePanel = ConsoleOutputPanel(project, toolWindow)
        toolWindow.contentManager.addContent(
            contentFactory.createContent(lineagePanel.getContent(), "dbt lineage", false),
        )
        toolWindow.contentManager.addContent(
            contentFactory.createContent(docsPanel.getContent(), "dbt docs", false),
        )
        toolWindow.contentManager.addContent(
            contentFactory.createContent(consolePanel.getContent(), "console", false),
        )
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
        // call service to check if it is a valid dbt project, e.g. can find dbt_project.yml etc
        // if it is not a valid dbt project throw warning (also to the UI) and return false
        return true
    }

    private fun handleTabChange(
        project: Project,
        event: ContentManagerEvent,
    ) {
        // handle tab change impl
    }

    private fun showLoadingIndicator(
        project: Project,
        task: () -> Unit,
    ) {
        val dbtParseTask =
            object : Task.Backgroundable(project, "Executing dbt parse...", false) {
                override fun run(indicator: ProgressIndicator) {
                    indicator.isIndeterminate = true // Set indeterminate mode to show loading animation
                    task.invoke()
                }
            }
        ProgressManager.getInstance().run(dbtParseTask)
    }
}
