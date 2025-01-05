package tech.toshitworks.attendo.presentation.screen.form_screen

import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SemesterModel
import tech.toshitworks.attendo.domain.model.SubjectModel

data class FormScreenStates(
    val semesterModel: SemesterModel? = SemesterModel(semNumber = 1, midTermDate = "", startDate = "", endDate = ""),
    val dayList: List<DayModel> = listOf(),
    val subjectList: List<SubjectModel> = listOf(),
    val periodList: List<PeriodModel> = listOf(),
)
