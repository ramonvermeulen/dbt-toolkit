package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.messages.Topic

interface ActiveFileListener {
    fun activeFileChanged(file: VirtualFile?)
}

@Service(Service.Level.PROJECT)
class ActiveFileService(private val project: Project) : DumbAware {
    private var publisher: ActiveFileListener = project.messageBus.syncPublisher(TOPIC)

    fun setActiveFile(file: VirtualFile) {
        // so events will only get published once the project indices are loaded
        DumbService.getInstance(project).runWhenSmart {
            publisher.activeFileChanged(file)
        }
    }

    companion object {
        val TOPIC = Topic.create("ActiveFileTopic", ActiveFileListener::class.java)
    }
}