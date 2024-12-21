package tech.toshitworks.attendancechahiye.domain.model

data class TimetableModel(
    val id: Long = 0,
    val subject: SubjectModel,
    val day: DayModel,
    val period: PeriodModel,
    val deletedForDates: List<String>? = null,
    val editedForDates: List<String>? = null
)