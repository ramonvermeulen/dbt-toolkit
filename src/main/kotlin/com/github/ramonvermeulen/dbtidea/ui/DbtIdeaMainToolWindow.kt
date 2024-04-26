package com.github.ramonvermeulen.dbtidea.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.ContentManagerEvent
import com.intellij.ui.content.ContentManagerListener


class DbtIdeaMainToolWindow : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()
        val lineageToolWindow = LineagePanel(project, toolWindow)
        toolWindow.contentManager.addContent(
            contentFactory.createContent(lineageToolWindow.getContent(), "dbt lineage", false)
        )
        toolWindow.contentManager.addContentManagerListener(
            object : ContentManagerListener {
                override fun selectionChanged(event: ContentManagerEvent) {
                    // handle selection events
                    println(event.toString())
                }
            }
        )
    }

    override fun shouldBeAvailable(project: Project) = true

    override fun isApplicable(project: Project): Boolean {
        // call service to check if it is a valid dbt project, e.g. can find dbt_project.yml etc
        // if it is not a valid dbt project throw warning (also to the UI) and return false
        return true
    }
}

