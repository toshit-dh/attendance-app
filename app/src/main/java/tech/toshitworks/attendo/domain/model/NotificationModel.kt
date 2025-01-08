package tech.toshitworks.attendo.domain.model

data class NotificationModel(
    val id: Long = 0,
    val title: String,
    val subText: String,
    val message: String,
    val timestamp: Long,
)
