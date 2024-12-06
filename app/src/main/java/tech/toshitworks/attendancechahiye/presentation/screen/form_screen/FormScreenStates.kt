package tech.toshitworks.attendancechahiye.presentation.screen.form_screen

import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SemesterModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

data class FormScreenStates(
    val semesterModel: SemesterModel? = SemesterModel(semNumber = 1, midTermDate = "", startDate = "", endDate = ""),
    val dayList: List<DayModel> = listOf(),
    val subjectList: List<SubjectModel> = listOf(),
    val periodList: List<PeriodModel> = listOf(),
)
