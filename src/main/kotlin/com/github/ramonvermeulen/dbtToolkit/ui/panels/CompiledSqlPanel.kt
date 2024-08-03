package com.github.ramonvermeulen.dbtToolkit.ui.panels

import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileListener
import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileService
import com.github.ramonvermeulen.dbtToolkit.services.DbtCommandExecutorService
import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.github.ramonvermeulen.dbtToolkit.ui.IdeaPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import java.awt.BorderLayout
import java.nio.file.Paths
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class CompiledSqlPanel(project: Project) : IdeaPanel, Disposable, ActiveFileListener {
    private val settings = project.service<DbtToolkitSettingsService>()
    private val dbtCommandExecutorService = project.service<DbtCommandExecutorService>()
    private val mainPanel = JPanel(BorderLayout())
    private val recompileButton = JButton("Re-compile model")
    private var document: Document? = null
    private var activeFile: VirtualFile? = null

    init {
        project.messageBus.connect().subscribe(ActiveFileService.TOPIC, this)
        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("sql")
        document = EditorFactory.getInstance().createDocument("")
        val editor = EditorFactory.getInstance().createEditor(document!!, project, fileType, true)
        val editorTextField = editor.component
        // to set the initial file, since the subscription is only set-up after
        // opening the panel (lazy) for the first time
        activeFileChanged(FileEditorManager.getInstance(project).selectedFiles.firstOrNull())
        recompileButton.addActionListener { handleRecompileButtonClick() }
        mainPanel.add(recompileButton, BorderLayout.NORTH)
        mainPanel.add(editorTextField, BorderLayout.CENTER)
    }

    private fun handleRecompileButtonClick() {
        SwingUtilities.invokeLater {
            recompileButton.isEnabled = false
            recompileButton.text = "Re-compiling..."
        }
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                if (activeFile != null) {
                    // save the active file on disk before recompiling, IDE can have an open buffer
                    ApplicationManager.getApplication().invokeLater {
                        ApplicationManager.getApplication().runWriteAction {
                            FileDocumentManager.getInstance().getDocument(activeFile!!).let {
                                FileDocumentManager.getInstance().saveDocument(it!!)
                            }
                        }
                    }
                    dbtCommandExecutorService.executeCommand(
                        listOf("compile", "--no-populate-cache", "--select", activeFile!!.nameWithoutExtension),
                    )
                    activeFileChanged(activeFile)
                }
            } finally {
                SwingUtilities.invokeLater {
                    recompileButton.isEnabled = true
                    recompileButton.text = "Re-compile model"
                }
            }
        }
    }

    override fun activeFileChanged(file: VirtualFile?) {
        activeFile = file
        val compiledFile = findCompiledFile(file)
        displayCompiledFile(compiledFile)
    }

    private fun findCompiledFile(file: VirtualFile?): VirtualFile? {
        val dbtProjectRoot = Paths.get(settings.state.dbtProjectsDir).parent
        val relativePathFromDbtProjectsRoot = file?.path?.let { Paths.get(it) }?.let { dbtProjectRoot.relativize(it) }
        val targetPath = Paths.get(settings.state.dbtTargetDir, "compiled", relativePathFromDbtProjectsRoot.toString())
        return VirtualFileManager.getInstance().findFileByUrl(targetPath.toUri().toString())
    }

    private fun displayCompiledFile(file: VirtualFile?) {
        if (file != null && file.exists()) {
            // VirtualFiles are cached by default, so we need to refresh the file to get the latest contents
            file.refresh(false, false)
            SwingUtilities.invokeLater {
                ApplicationManager.getApplication().runWriteAction {
                    document?.setText(file.contentsToByteArray().toString(Charsets.UTF_8))
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
