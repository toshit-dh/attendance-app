package tech.toshitworks.attendo.presentation.screen.timetable_screen

import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.model.TimetableModel

data class TimeTableScreenStates(
    val isLoading: Boolean = true,
    val dayList: List<DayModel> = listOf(),
    val filled: Int = 0,
    val total: Int = 0,
    val subjectList: List<SubjectModel> = listOf(),
    val periodList: List<PeriodModel> = listOf(),
    val listPeriods: List<TimetableModel?> = listOf()
)