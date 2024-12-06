package tech.toshitworks.attendancechahiye.presentation.screen.timetable_screen

import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

sealed class TimeTableScreenEvents {
    data class OnAddPeriod(val timetableModel: TimetableModel,val index: Int): TimeTableScreenEvents()
    data object OnNextClick: TimeTableScreenEvents()
}