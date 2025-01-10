package tech.toshitworks.attendo.data.repository

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import tech.toshitworks.attendo.domain.repository.NotificationWorkRepository
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.workers.NotificationWorker
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationWorkRepoImpl @Inject constructor(
    private val workManager: WorkManager
)
    : NotificationWorkRepository {
    override fun enqueueNotificationWorker(id: Int,screen: ScreenRoutes,title: String,subText: String, content: String, channelId: String,delay: Long): UUID {
        val workData = workDataOf(
            "id" to id,
            "title" to title,
            "subText" to subText,
            "content" to content,
            "channelId" to channelId,
            "screen" to screen.route
        )
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(workData)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueue(workRequest)
        return workRequest.id
    }

    override fun cancelNotificationWork(workId: UUID?) {
        workId?.let {
            workManager.cancelWorkById(workId)
        }
    }
}