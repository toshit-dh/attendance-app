package tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen

import tech.toshitworks.attendancechahiye.domain.model.AnalyticsModel

data class AnalyticsScreenState(
    val isLoading: Boolean = true,
    val analyticsList: List<AnalyticsModel> = emptyList(),
)