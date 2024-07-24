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
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import java.awt.BorderLayout
import java.io.File
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
        val editor = EditorFactory.getInstance().createEditor(document!!, project, fileType, false)
        val editorTextField = editor.component
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
        // todo(ramon) reconsider alternative to basePath
        val relativePathFromDbtProjectRoot = file?.path?.replace(File(settings.state.dbtProjectsDir).parentFile.path, "")
        val targetPath = settings.state.dbtTargetDir + "/compiled" + relativePathFromDbtProjectRoot
        return VirtualFileManager.getInstance().findFileByUrl("file://$targetPath")
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
