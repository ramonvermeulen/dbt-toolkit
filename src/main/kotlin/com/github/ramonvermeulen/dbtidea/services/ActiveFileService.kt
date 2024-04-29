package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.vfs.VirtualFile

@Service(Service.Level.PROJECT)
class ActiveFileService {
    private var activeFile: VirtualFile? = null

    fun setActiveFile(file: VirtualFile) {
        activeFile = file
    }

    fun getActiveFile(): VirtualFile? = activeFile
}