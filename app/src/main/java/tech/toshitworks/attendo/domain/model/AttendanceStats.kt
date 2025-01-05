package tech.toshitworks.attendo.domain.model

data class AttendanceStats(
    val totalLectures: Int,
    val totalPresent: Int,
    val attendancePercentage: Double
)