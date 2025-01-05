package tech.toshitworks.attendo.presentation.screen.analytics_screen

import tech.toshitworks.attendo.domain.model.AnalyticsModel
import tech.toshitworks.attendo.domain.model.AttendanceModel

data class AnalyticsScreenState(
    val isLoading: Boolean = true,
    val attendanceList: List<AttendanceModel> = listOf(),
    val filteredAttendanceList: List<AttendanceModel> = listOf(),
    val analyticsList: List<AnalyticsModel> = emptyList(),
    val startDate: Long = 0,
    val endDate: Long = 0,
)