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
    private var publisher: ActiveFileListener = project.messageBus.syncPublisher(TOPIC)

    fun setActiveFile(file: VirtualFile) {
        activeFile = file
        publisher.activeFileChanged(file)
    }

    fun getActiveFile(): VirtualFile? = activeFile

    companion object {
        val TOPIC = Topic.create("ActiveFileTopic", ActiveFileListener::class.java)
    }
}