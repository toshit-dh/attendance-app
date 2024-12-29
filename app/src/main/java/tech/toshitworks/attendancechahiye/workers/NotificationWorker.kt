package tech.toshitworks.attendancechahiye.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendancechahiye.di.RepoEntryPoint

class NotificationWorker(
    context: Context,
    workParams: WorkerParameters
): CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        val notifRepo = EntryPointAccessors.fromApplication(applicationContext,RepoEntryPoint::class.java)
            .notificationRepository()
        return try {
            val title = inputData.getString("title")!!
            val content = inputData.getString("content")!!
            val channelId = inputData.getString("channelId")!!
            notifRepo.showNotification(title,content,channelId)
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}