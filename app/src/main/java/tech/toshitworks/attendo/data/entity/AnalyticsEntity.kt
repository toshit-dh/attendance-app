package tech.toshitworks.attendo.data.entity

import androidx.room.ColumnInfo

data class AnalyticsEntity(
    @ColumnInfo(name = "subjectId") val subjectId: Long?,
    val lecturesPresent: Int,
    val lecturesTaken: Int
)

data class AnalyticsByDay(
    @ColumnInfo(name = "subjectId") val subjectId: Long?,
    @ColumnInfo(name = "dayId") val dayId: Long,
    @ColumnInfo(name = "dayName") val day: String,
    val lecturesConducted: Int,
    val lecturesPresent: Int,
)

data class AnalyticsByWeek(
    @ColumnInfo(name = "subjectId") val subjectId: Long?,
    @ColumnInfo(name = "week") val yearWeek: String,
    val lecturesConducted: Int,
    val lecturesPresent: Int,
)

data class AnalyticsByMonth(
    @ColumnInfo(name = "subjectId") val subjectId: Long?,
    @ColumnInfo(name = "month") val yearMonth: String,
    val lecturesConducted: Int,
    val lecturesPresent: Int,

)