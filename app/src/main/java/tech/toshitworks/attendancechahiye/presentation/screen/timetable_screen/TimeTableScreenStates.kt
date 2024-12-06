package tech.toshitworks.attendancechahiye.presentation.screen.timetable_screen

import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

data class TimeTableScreenStates(
    val isLoading: Boolean = true,
    val dayList: List<DayModel> = listOf(),
    val subjectList: List<SubjectModel> = listOf(),
    val periodList: List<PeriodModel> = listOf(),
    val listPeriods: List<TimetableModel?> = listOf()
)