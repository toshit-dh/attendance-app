package tech.toshitworks.attendo.domain.model

import java.time.LocalDate
import java.util.UUID

data class EventModel(
    val id: Long = 0,
    val date: String,
    val dateLocal: LocalDate? = null,
    val subjectModel: SubjectModel,
    val content: String,
    val notificationWorkId: UUID? = null
)
