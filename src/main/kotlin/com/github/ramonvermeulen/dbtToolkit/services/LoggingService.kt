package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic

data class LoggingEvent(val message: String, val type: ConsoleViewContentType)

interface LoggingListener {
    fun logEvent(event: LoggingEvent)

    fun flush() { /* optional */ }
}

@Service(Service.Level.PROJECT)
class LoggingService(project: Project) {
    private val publisher: LoggingListener = project.messageBus.syncPublisher(TOPIC)

    init {
        log(
            "dbtToolkit executes dbt commands under the hood for generating artifacts,\n" +
                "in case something went wrong during background execution you will see the error message printed " +
                "in this console.\n",
            ConsoleViewContentType.NORMAL_OUTPUT,
        )
    }

    fun log(
        message: String,
        type: ConsoleViewContentType,
    ) {
        publisher.logEvent(LoggingEvent(message, type))
    }

    companion object {
        val TOPIC = Topic("LoggingTopic", LoggingListener::class.java)
    }
}
