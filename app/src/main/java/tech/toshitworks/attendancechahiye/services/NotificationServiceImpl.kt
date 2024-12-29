package tech.toshitworks.attendancechahiye.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.toshitworks.attendancechahiye.MainActivity
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.domain.repository.NotificationService
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    private val notificationManager: NotificationManager,
    @ApplicationContext private val context: Context
): NotificationService {
    override fun showNotification(title: String, message: String, channelId: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("screen",ScreenRoutes.EventsScreen.route)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.drawable.event)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1,builder)
    }
}