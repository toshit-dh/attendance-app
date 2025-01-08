package tech.toshitworks.attendo.domain.repository

import java.util.UUID

interface NotificationWorkRepository {

    fun enqueueNotificationWorker(id: Int,title: String,subText: String, content: String, channelId: String,delay: Long):  UUID

    fun cancelNotificationWork(workId: UUID?)

}