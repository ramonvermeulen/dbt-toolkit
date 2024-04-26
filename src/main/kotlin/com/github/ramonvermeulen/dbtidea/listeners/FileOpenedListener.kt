package com.github.ramonvermeulen.dbtidea.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile

class FileOpenedListener : FileEditorManagerListener {
    override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
        print(file.name)
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        print(file.name)
    }
}