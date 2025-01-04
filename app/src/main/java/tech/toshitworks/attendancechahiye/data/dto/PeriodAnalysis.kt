package tech.toshitworks.attendancechahiye.data.dto

data class PeriodAnalysis(
    val periodId: Long,
    val startTime: String,
    val endTime: String,
    val lecturesConducted: Int,
    val lecturesPresent: Int
)
