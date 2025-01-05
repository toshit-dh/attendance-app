package tech.toshitworks.attendo.presentation.screen.edit_info_screen

import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SemesterModel
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.model.TimetableModel

data class EditInfoScreenStates(
    val isLoading: Boolean = true,

    val semesterModel: SemesterModel? = null,
    val changedSemesterModel: SemesterModel? = null,

    val periodList: List<PeriodModel> = listOf(),
    val listPeriods: List<TimetableModel?> = listOf(),
    val dayList: List<DayModel> = listOf(),

    val subjectList: List<SubjectModel> = listOf(),
    val changedSubjectList: List<SubjectModel> = listOf(),

    val timetable: List<TimetableModel> = listOf(),
    val changedTimetable: List<TimetableModel?> = listOf(),
)
