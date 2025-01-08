package tech.toshitworks.attendo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val eventName = getString(R.string.event_notification_channel)
        val eventDescriptionText = getString(R.string.event_notification_description)
        val eventChannel = NotificationChannel(getString(R.string.event_channel_id),eventName, importance).apply {
            description = eventDescriptionText
        }

        val markName = getString(R.string.mark_notification_channel)
        val markDescriptionText = getString(R.string.mark_notification_description)
        val markChannel = NotificationChannel(getString(R.string.mark_channel_id),markName, importance).apply {
            description = markDescriptionText
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(eventChannel)
        notificationManager.createNotificationChannel(markChannel)
    }
}