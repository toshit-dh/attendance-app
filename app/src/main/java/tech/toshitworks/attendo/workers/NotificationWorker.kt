package tech.toshitworks.attendo.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendo.di.RepoEntryPoint

class NotificationWorker(
    context: Context,
    workParams: WorkerParameters
): CoroutineWorker(context, workParams) {

    override suspend fun doWork(): Result {
        val notifRepo = EntryPointAccessors.fromApplication(applicationContext,RepoEntryPoint::class.java)
            .notificationRepository()
        return try {
            val id = inputData.getInt("id",1)
            val title = inputData.getString("title")!!
            val subText = inputData.getString("subText")!!
            val content = inputData.getString("content")!!
            val channelId = inputData.getString("channelId")!!
            notifRepo.showNotification(id,title,subText,content,channelId)
            Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }
}