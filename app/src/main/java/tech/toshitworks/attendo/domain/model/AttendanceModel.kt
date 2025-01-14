package tech.toshitworks.attendo.domain.model

data class AttendanceModel(
    val id: Long = 0,
    val day: DayModel? = null,
    val subject: SubjectModel? = null,
    val period: PeriodModel,
    val date: String,
    val isPresent: Boolean,
    val deleted: Boolean = false
)
