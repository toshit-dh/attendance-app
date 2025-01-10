package tech.toshitworks.attendo.domain.repository

import tech.toshitworks.attendo.navigation.ScreenRoutes
import java.util.UUID

interface NotificationWorkRepository {

    fun enqueueNotificationWorker(id: Int,screen: ScreenRoutes,title: String, subText: String, content: String, channelId: String, delay: Long):  UUID

    fun cancelNotificationWork(workId: UUID?)

}