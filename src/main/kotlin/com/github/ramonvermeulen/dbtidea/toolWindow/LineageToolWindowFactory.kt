package com.github.ramonvermeulen.dbtidea.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.ramonvermeulen.dbtidea.DbtIdeaBundle
import javax.swing.JButton


class LineageToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val lineageToolWindow = LineageToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(lineageToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    override fun isApplicable(project: Project): Boolean {
        return true
    }

    class LineageToolWindow(toolWindow: ToolWindow) {
        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel("Hello World")

            add(label)
        }
    }
}
