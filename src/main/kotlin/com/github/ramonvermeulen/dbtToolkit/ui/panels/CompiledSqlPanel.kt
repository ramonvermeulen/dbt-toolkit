package com.github.ramonvermeulen.dbtToolkit.ui.panels

import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileListener
import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileService
import com.github.ramonvermeulen.dbtToolkit.services.DbtCommandExecutorService
import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.github.ramonvermeulen.dbtToolkit.ui.IdeaPanel
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
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
    private var document = EditorFactory.getInstance().createDocument("")
    private val editor = EditorFactory.getInstance().createEditor(document, project, FileTypeManager.getInstance().getFileTypeByExtension("sql"), true)
    private var activeFile: VirtualFile? = null

    init {
        project.messageBus.connect(project).subscribe(ActiveFileService.TOPIC, this)
        val editorTextField = editor.component
        // to set the initial file, since the subscription is only set-up after
        // opening the panel (lazy) for the first time
        activeFileChanged(FileEditorManager.getInstance(project).selectedFiles.firstOrNull())
        recompileButton.addActionListener { handleRecompileButtonClick() }
        mainPanel.add(recompileButton, BorderLayout.NORTH)
        mainPanel.add(editorTextField, BorderLayout.CENTER)
        Disposer.register(project, this)
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
                    val command = if (settings.state.dbtVersionMajor != null && settings.state.dbtVersionMinor != null && settings.state.dbtVersionMajor!! <= 1 && settings.state.dbtVersionMinor!! < 5) {
                        // --no-populate-cache is only available in dbt >= 1.5
                        listOf("compile", "--select", activeFile!!.nameWithoutExtension)
                    } else {
                        listOf("compile", "--no-populate-cache", "--select", activeFile!!.nameWithoutExtension)
                    }
                    dbtCommandExecutorService.executeCommand(command)
                }
            } finally {
                activeFileChanged(activeFile)
                SwingUtilities.invokeLater {
                    recompileButton.isEnabled = true
                    recompileButton.text = "Re-compile model"
                }
            }
        }
    }

    override fun activeFileChanged(file: VirtualFile?) {
        if (file != null && file.extension == "sql") {
            activeFile = file
            val compiledFile = findCompiledFile(file)
            displayCompiledFile(compiledFile)
            SwingUtilities.invokeLater {
                recompileButton.isEnabled = true
                recompileButton.text = "Re-compile model"
            }
        } else {
            SwingUtilities.invokeLater {
                recompileButton.isEnabled = false
                recompileButton.text = "Open a SQL file"
                ApplicationManager.getApplication().runWriteAction {
                    document.setText("")
                }
            }
        }
    }

    private fun findCompiledFile(file: VirtualFile): VirtualFile? {
        return file.let { nonNullFile ->
            settings.state.dbtModelPaths.map { Regex("$it.*").find(nonNullFile.path)?.value }
                .firstOrNull { it != null }
                ?.let { relativePath ->
                    val targetPath = Paths.get(settings.state.dbtTargetDir, "compiled", settings.state.dbtProjectName, relativePath)
                    VirtualFileManager.getInstance().refreshAndFindFileByUrl(targetPath.toUri().toString())
                }
        }
    }

    private fun displayCompiledFile(file: VirtualFile?) {
        if (file != null && file.exists()) {
            // VirtualFiles are cached by default, so we need to refresh the file to get the latest contents
            file.refresh(false, false)
            SwingUtilities.invokeLater {
                ApplicationManager.getApplication().runWriteAction {
                    val fileContent = file.contentsToByteArray().toString(Charsets.UTF_8)
                    // On windows line separators are typically \r\n instead of \n
                    // This can cause issues with the editor, so we normalize the line separators
                    val normalizedContent = fileContent.replace("\r\n", "\n")
                    document.setText(normalizedContent)
                }
            }
        } else {
            SwingUtilities.invokeLater {
                ApplicationManager.getApplication().runWriteAction {
                    document.setText("No compiled file found. Please compile the model first by clicking the 'Re-compile model' button.")
                }
            }
        }
    }

    override fun getContent(): JComponent {
        return mainPanel
    }

    override fun dispose() {
        EditorFactory.getInstance().releaseEditor(editor)
        mainPanel.removeAll()
    }
}
