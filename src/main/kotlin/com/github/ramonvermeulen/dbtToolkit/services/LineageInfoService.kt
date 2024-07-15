package com.github.ramonvermeulen.dbtToolkit.services

import com.github.ramonvermeulen.dbtToolkit.models.LineageInfo
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic

interface LineageInfoListener {
    fun lineageInfoChanged(newLineageInfo: LineageInfo)
}

@Service(Service.Level.PROJECT)
class LineageInfoService(private val project: Project) : DumbAware {
    private var publisher: LineageInfoListener = project.messageBus.syncPublisher(TOPIC)

    fun setLineageInfo(lineageInfo: LineageInfo) {
        DumbService.getInstance(project).runWhenSmart {
            publisher.lineageInfoChanged(lineageInfo)
        }
    }

    companion object {
        val TOPIC = Topic.create("LineageInfoTopic", LineageInfoListener::class.java)
    }
}
