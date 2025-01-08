package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.domain.model.NotificationModel

interface NotificationRepository {

    suspend fun insertNotification(notificationModel: NotificationModel)

    suspend fun getNotifications(): List<NotificationModel>

}