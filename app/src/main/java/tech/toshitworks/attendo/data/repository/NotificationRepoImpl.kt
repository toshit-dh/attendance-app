package tech.toshitworks.attendo.data.repository

import tech.toshitworks.attendo.data.entity.NotificationEntity
import tech.toshitworks.attendo.data.local.NotificationDao
import tech.toshitworks.attendo.domain.model.NotificationModel
import tech.toshitworks.attendo.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    override suspend fun insertNotification(notificationModel: NotificationModel) {
        notificationDao.insertNotification(
            NotificationEntity(
                id = notificationModel.id,
                title = notificationModel.title,
                subText = notificationModel.subText,
                timestamp = notificationModel.timestamp,
                message = notificationModel.message
            )
        )
    }

    override suspend fun getNotifications(): List<NotificationModel> {
        return notificationDao.getNotifications().map {
            NotificationModel(
                id = it.id,
                title = it.title,
                subText = it.subText,
                timestamp = it.timestamp,
                message = it.message,
            )
        }
    }
}