package com.github.ramonvermeulen.dbtidea.listeners

import com.github.ramonvermeulen.dbtidea.services.ActiveFileService
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project

class DbtIdeaFileListener(private val project: Project) : FileEditorManagerListener {
    private val activeFileService = project.service<ActiveFileService>()
    companion object {
        val SUPPORTED_EXTENSIONS = setOf("sql", "yml", "csv")
    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        if (SUPPORTED_EXTENSIONS.contains(event.newFile.extension)) {
            activeFileService.setActiveFile(event.newFile)
        }
    }
}
