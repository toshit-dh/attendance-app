package tech.toshitworks.attendancechahiye.domain.repository

import java.util.UUID

interface NotificationWorkRepository {

    fun enqueueNotificationWorker(title: String, content: String, channelId: String,delay: Long):  UUID

    fun cancelNotificationWork(workId: UUID?)

}