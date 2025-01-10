package tech.toshitworks.attendo.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendo.di.RepoEntryPoint
import tech.toshitworks.attendo.domain.model.NotificationModel

class NotificationWorker(
    context: Context,
    workParams: WorkerParameters
): CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        return try {
            val repoEntryPoint = EntryPointAccessors.fromApplication(applicationContext,RepoEntryPoint::class.java)
            val notifWorkRepo = repoEntryPoint.notificationService()
            val notifRepo = repoEntryPoint.notificationRepository()
            val id = inputData.getInt("id",1)
            val title = inputData.getString("title")!!
            val subText = inputData.getString("subText")!!
            val content = inputData.getString("content")!!
            val channelId = inputData.getString("channelId")!!
            val screen = inputData.getString("screen")!!
            notifWorkRepo.showNotification(
                id,
                screen,
                title,
                subText,
                content,
                channelId
            )
            notifRepo.insertNotification(NotificationModel(
                title = title,
                subText = subText,
                timestamp = System.currentTimeMillis(),
                message = content
            ))
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}