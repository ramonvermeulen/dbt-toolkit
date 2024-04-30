package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.messages.Topic

interface ActiveFileListener {
    fun activeFileChanged(file: VirtualFile?)
}

@Service(Service.Level.PROJECT)
class ActiveFileService(private val project: Project) {
    private var activeFile: VirtualFile? = null

    fun setActiveFile(file: VirtualFile) {
        activeFile = file
        project.messageBus.syncPublisher(TOPIC).activeFileChanged(file)
    }

    fun getActiveFile(): VirtualFile? = activeFile

    companion object {
        val TOPIC = Topic.create("ActiveFileTopic", ActiveFileListener::class.java)
    }
}