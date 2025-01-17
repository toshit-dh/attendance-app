package tech.toshitworks.attendo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import tech.toshitworks.attendo.utils.createNotificationChannel

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }
}