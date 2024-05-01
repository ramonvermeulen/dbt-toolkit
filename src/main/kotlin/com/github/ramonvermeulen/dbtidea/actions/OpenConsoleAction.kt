package com.github.ramonvermeulen.dbtidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager

class OpenConsoleAction : AnAction("Open Console") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project != null) {
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("dbtIdea")
            val contentManager = toolWindow?.contentManager
            val content = contentManager?.findContent("console (read-only)") // replace with your tab title
            if (content != null) {
                contentManager.setSelectedContent(content)
            }
        }
    }
}