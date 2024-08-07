package com.github.ramonvermeulen.dbtToolkit.ui.panels

import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileListener
import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileService
import com.github.ramonvermeulen.dbtToolkit.services.DbtCommandExecutorService
import com.github.ramonvermeulen.dbtToolkit.ui.IdeaPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class PreviewDataPanel(project: Project) : IdeaPanel, Disposable, ActiveFileListener {
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private val mainPanel = JPanel(BorderLayout())
    private val previewDataButton = JButton("Preview Data")
    private var document = EditorFactory.getInstance().createDocument("")
    private var activeFile: VirtualFile? = null

    init {
        project.messageBus.connect().subscribe(ActiveFileService.TOPIC, this)
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("txt")
        val editor = EditorFactory.getInstance().createEditor(document, project, fileType, true)
        val editorTextField = editor.component
        // to set the initial file, since the subscription is only set-up after
        // opening the panel (lazy) for the first time
        activeFileChanged(FileEditorManager.getInstance(project).selectedFiles.firstOrNull())
        previewDataButton.addActionListener { handleRecompileButtonClick() }
        mainPanel.add(previewDataButton, BorderLayout.NORTH)
        mainPanel.add(editorTextField, BorderLayout.CENTER)
    }

    private fun handleRecompileButtonClick() {
        SwingUtilities.invokeLater {
            previewDataButton.isEnabled = false
            previewDataButton.text = "Sending query to DWH..."
        }
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                if (activeFile != null) {
                    // save the active file on disk before sending Query, IDE can have an open buffer
                    ApplicationManager.getApplication().invokeLater {
                        ApplicationManager.getApplication().runWriteAction {
                            FileDocumentManager.getInstance().getDocument(activeFile!!).let {
                                FileDocumentManager.getInstance().saveDocument(it!!)
                            }
                        }
                    }
                }
            } finally {
                previewData()
            }
        }
    }

    override fun activeFileChanged(file: VirtualFile?) {
        if (file != null && file.extension == "sql") {
            activeFile = file
            SwingUtilities.invokeLater {
                previewDataButton.isEnabled = true
                previewDataButton.text = "Preview Data"
            }
        } else {
            SwingUtilities.invokeLater {
                previewDataButton.isEnabled = false
                previewDataButton.text = "Open a SQL file"
                ApplicationManager.getApplication().runWriteAction {
                    document.setText("")
                }
            }
        }
    }

    private fun previewData() {
        ApplicationManager.getApplication().executeOnPooledThread {
            val output =
                dbtCommandExecutorService.executeCommand(
                    listOf("show", "--no-populate-cache", "--select", activeFile!!.nameWithoutExtension, "--limit", "10"),
                )
            if (output.first == 0) {
                val data = output.second.split("\n").takeLast(16).joinToString("\n").trimEnd()
                SwingUtilities.invokeLater {
                    ApplicationManager.getApplication().runWriteAction {
                        document.setText(data)
                        previewDataButton.isEnabled = true
                        previewDataButton.text = "Preview Data"
                    }
                }
            } else {
                SwingUtilities.invokeLater {
                    ApplicationManager.getApplication().runWriteAction {
                        document.setText(
                            "Error occured during execution of \"dbt show\" command. " +
                                "Please check the logs in the console tab.",
                        )
                        previewDataButton.isEnabled = true
                        previewDataButton.text = "Preview Data"
                    }
                }
            }
        }
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {
        // Implement your dispose logic here
    }
}
