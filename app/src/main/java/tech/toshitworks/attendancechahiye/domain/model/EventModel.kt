package tech.toshitworks.attendancechahiye.domain.model

data class EventModel(
    val id: Long = 0,
    val date: String,
    val subjectModel: SubjectModel,
    val content: String
)
