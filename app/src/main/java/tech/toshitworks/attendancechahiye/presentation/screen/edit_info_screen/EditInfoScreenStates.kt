package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SemesterModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

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
