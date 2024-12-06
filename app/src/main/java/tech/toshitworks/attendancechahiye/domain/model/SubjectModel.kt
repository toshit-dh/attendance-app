package tech.toshitworks.attendancechahiye.domain.model

data class SubjectModel(
    val id: Long = 0,
    val name: String,
    val facultyName: String,
    val isAttendanceCounted: Boolean = true
)
