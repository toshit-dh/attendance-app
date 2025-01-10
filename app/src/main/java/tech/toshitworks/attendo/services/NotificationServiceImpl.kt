package tech.toshitworks.attendo.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.toshitworks.attendo.MainActivity
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.domain.repository.NotificationService
import tech.toshitworks.attendo.navigation.ScreenRoutes
import javax.inject.Inject

class NotificationServiceImpl @Inject constructor(
    private val notificationManager: NotificationManager,
    @ApplicationContext private val context: Context
): NotificationService {
    override fun showNotification(id: Int,screen: String,title: String,subText: String, message: String, channelId: String) {
        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val screenIntent = when{
                ScreenRoutes.EventsScreen.route == screen -> {
                    intent.putExtra("screen",ScreenRoutes.EventsScreen.route)
                }
                ScreenRoutes.TodayAttendance.route == screen -> {
                    intent.putExtra("screen",ScreenRoutes.TodayAttendance.route)
                }
                else -> {
                    intent.putExtra("screen",ScreenRoutes.TodayAttendance.route)
                }
            }
            val icon = when{
                ScreenRoutes.EventsScreen.route == screen -> {
                    R.drawable.event
                }
                ScreenRoutes.TodayAttendance.route == screen -> {
                    R.drawable.percent
                }
                else -> {
                    R.drawable.img
                }
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                screenIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val builder = NotificationCompat.Builder(context,channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setSubText(subText)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(id,builder)
        } catch (e: Exception) {
            Log.e("NotificationService", "Error showing notification: ${e.message}", e)
        }
    }
}