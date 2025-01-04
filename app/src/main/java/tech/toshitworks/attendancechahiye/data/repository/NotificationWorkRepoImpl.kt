package tech.toshitworks.attendancechahiye.data.repository

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import tech.toshitworks.attendancechahiye.domain.repository.NotificationWorkRepository
import tech.toshitworks.attendancechahiye.workers.NotificationWorker
import java.util.UUID
import javax.inject.Inject

class NotificationWorkRepoImpl @Inject constructor(
    private val workManager: WorkManager
)
    : NotificationWorkRepository {
    override fun enqueueNotificationWorker(id: Int,title: String,subText: String, content: String, channelId: String,delay: Long): UUID {
        val workData = workDataOf(
            "id" to id,
            "title" to title,
            "subText" to subText,
            "content" to content,
            "channelId" to channelId
        )
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(workData)
            .setInitialDelay(delay,java.util.concurrent.TimeUnit.MILLISECONDS)
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