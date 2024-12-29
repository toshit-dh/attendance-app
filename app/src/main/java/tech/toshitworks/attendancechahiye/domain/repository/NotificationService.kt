package tech.toshitworks.attendancechahiye.domain.repository

interface NotificationService {
    fun showNotification(title: String, message: String, channelId: String)
}