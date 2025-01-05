package tech.toshitworks.attendo.data.dto

data class AttendanceStats(
    val totalLectures: Int,
    val totalPresent: Int,
    val attendancePercentage: Double
)