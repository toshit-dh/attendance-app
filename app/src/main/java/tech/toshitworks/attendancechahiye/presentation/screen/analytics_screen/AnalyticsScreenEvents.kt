package tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen

sealed class AnalyticsScreenEvents {

    data class OnChangeSubject(val subjectId: Long): AnalyticsScreenEvents()

}