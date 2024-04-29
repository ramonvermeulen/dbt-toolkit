package com.github.ramonvermeulen.dbtidea.services

import com.intellij.notification.Notification
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class NotificationService(private val project: Project) {
    fun sendNotification(
        title: String,
        content: String,
        notificationListener: NotificationListener? = null,
    ) {
        val notification = Notification("com.github.ramonvermeulen.dbtIdea", title, content, NotificationType.INFORMATION)
        Notifications.Bus.notify(notification)
    }
}
