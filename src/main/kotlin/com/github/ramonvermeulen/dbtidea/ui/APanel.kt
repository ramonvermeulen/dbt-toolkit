package com.github.ramonvermeulen.dbtidea.ui

import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import javax.swing.JComponent

abstract class APanel(private val project: Project, private val toolWindow: ToolWindow) {
    abstract fun getContent(): JComponent

    fun showLoadingIndicator(
        title: String,
        task: () -> Unit,
    ) {
        val dbtParseTask =
            object : Task.Backgroundable(project, title, false) {
                override fun run(indicator: ProgressIndicator) {
                    indicator.isIndeterminate = true
                    task.invoke()
                }
            }
        ProgressManager.getInstance().run(dbtParseTask)
    }
}
