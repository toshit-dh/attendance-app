package tech.toshitworks.attendo.presentation.screen.analytics_screen

import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.domain.model.SubjectModel

sealed class AnalyticsScreenEvents {

    data class OnDateRangeAttendance(val subjectModel: SubjectModel?,val fromDate: String,val toDate: String,val stats: (AttendanceStats)->Unit): AnalyticsScreenEvents()

}