package tech.toshitworks.attendancechahiye.data.entity

data class AttendanceStats(
    val totalLectures: Int,
    val totalPresent: Int,
    val attendancePercentage: Double
)