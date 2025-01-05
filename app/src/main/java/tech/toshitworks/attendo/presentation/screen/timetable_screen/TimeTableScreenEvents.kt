package tech.toshitworks.attendo.presentation.screen.timetable_screen

import tech.toshitworks.attendo.domain.model.TimetableModel

sealed class TimeTableScreenEvents {
    data class OnAddPeriod(val timetableModel: TimetableModel,val index: Int): TimeTableScreenEvents()
    data object OnNextClick: TimeTableScreenEvents()
}