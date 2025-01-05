package tech.toshitworks.attendo.domain.repository

interface NotificationService {
    fun showNotification(id: Int,title: String,subText: String, message: String, channelId: String)
}