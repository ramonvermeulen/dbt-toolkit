package com.github.ramonvermeulen.dbtidea.services

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class NotificationService(private val project: Project) {
    fun sendNotification(
        groupId: String,
        content: String,
        notificationType: NotificationType,
        action: AnAction? = null,
        closeOnAction: Boolean = true,
    ) {
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup(groupId)
            .createNotification(content, notificationType)

        action?.let {
            val wrappedAction = if (closeOnAction) {
                object : AnAction(it.templatePresentation.text) {
                    override fun actionPerformed(e: AnActionEvent) {
                        it.actionPerformed(e)
                        notification.expire()
                    }
                }
            } else {
                it
            }
            notification.addAction(wrappedAction)
        }

        notification.notify(project)
    }
}
