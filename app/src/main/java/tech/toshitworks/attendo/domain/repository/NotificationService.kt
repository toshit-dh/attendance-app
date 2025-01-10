package tech.toshitworks.attendo.domain.repository

interface NotificationService {
    fun showNotification(id: Int, screen: String, title: String, subText: String, message: String, channelId: String)
}