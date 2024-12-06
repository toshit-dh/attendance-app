package tech.toshitworks.attendancechahiye.presentation.screen.form_screen

import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

sealed class FormScreenEvents {

    data class OnSemesterNumberChange(val semNumber: Int) : FormScreenEvents()
    data class OnStartDateChange(val startDate: String) : FormScreenEvents()
    data class OnMidTermDateChange(val midTermDate: String) : FormScreenEvents()
    data class OnEndDateChange(val endDate: String) : FormScreenEvents()
    data class OnDayClick(val name: String,val add: Boolean) : FormScreenEvents()
    data class OnAddSubjectClick(val subjectModel: SubjectModel): FormScreenEvents()
    data class OnRemoveSubjectClick(val subjectModel: SubjectModel): FormScreenEvents()
    data class OnAddPeriodClick(val startTime: String,val endTime: String,val periodDuration: Int): FormScreenEvents()
    data object AddInRoom: FormScreenEvents()
}