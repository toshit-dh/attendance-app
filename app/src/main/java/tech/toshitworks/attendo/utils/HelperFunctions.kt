package tech.toshitworks.attendo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import tech.toshitworks.attendo.MainActivity
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.navigation.ScreenRoutes

fun updateShortcut(context: Context,shouldBeEnabled: Boolean) {
    val shortcutManager = getSystemService(context,ShortcutManager::class.java)
    if (shouldBeEnabled) {
        val timetableShortcut = ShortcutInfo.Builder(context, "view_timetable")
            .setShortLabel("View Timetable")
            .setLongLabel("View Timetable")
            .setIcon(Icon.createWithResource(context, R.drawable.timetable))
            .setIntent(Intent(context, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                putExtra("screen", ScreenRoutes.EditInfoScreen.route)
            })
            .build()
        val notesShortcut = ShortcutInfo.Builder(context, "view_notes")
            .setShortLabel("Notes")
            .setLongLabel("View Notes")
            .setIcon(Icon.createWithResource(context, R.drawable.notes))
            .setIntent(Intent(context, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                putExtra("screen", ScreenRoutes.NotesScreen.route)
            })
            .build()
        val eventsShortcut = ShortcutInfo.Builder(context, "view_events")
            .setShortLabel("Events")
            .setLongLabel("View Events")
            .setIcon(Icon.createWithResource(context, R.drawable.event))
            .setIntent(Intent(context, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                putExtra("screen", ScreenRoutes.EventsScreen.route)
            })
            .build()

        shortcutManager?.dynamicShortcuts = listOf(
            timetableShortcut,
            notesShortcut,
            eventsShortcut
        )
    } else {
        shortcutManager?.removeAllDynamicShortcuts()
    }
}

fun createNotificationChannel(context: Context) {

    val importance = NotificationManager.IMPORTANCE_DEFAULT

    val eventName = getString(context,R.string.event_notification_channel)
    val eventDescriptionText = getString(context,R.string.event_notification_description)
    val eventChannel = NotificationChannel(getString(context,R.string.event_channel_id),eventName, importance).apply {
        description = eventDescriptionText
    }

    val markName = getString(context,R.string.mark_notification_channel)
    val markDescriptionText = getString(context,R.string.mark_notification_description)
    val markChannel = NotificationChannel(getString(context,R.string.mark_channel_id),markName, importance).apply {
        description = markDescriptionText
    }

    val notificationManager = getSystemService(context,NotificationManager::class.java)
    notificationManager?.createNotificationChannel(eventChannel)
    notificationManager?.createNotificationChannel(markChannel)
}