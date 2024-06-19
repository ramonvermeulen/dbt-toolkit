package com.github.ramonvermeulen.dbtToolkit.listeners

import com.github.ramonvermeulen.dbtToolkit.services.ActiveFileService
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class DbtToolkitFileListener(private val project: Project) : FileEditorManagerListener {
    private val activeFileService = project.service<ActiveFileService>()
    companion object {
        val SUPPORTED_EXTENSIONS = setOf("sql", "csv")
    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        if (SUPPORTED_EXTENSIONS.contains(event.newFile?.extension)) {
            activeFileService.setActiveFile(event.newFile as VirtualFile)
        }
    }
}
