package tech.toshitworks.attendancechahiye.domain.model

data class SemesterModel(
    val id: Long = 0,
    val semNumber: Int,
    val midTermDate: String? = null,
    val startDate: String,
    val endDate: String? = null
)
