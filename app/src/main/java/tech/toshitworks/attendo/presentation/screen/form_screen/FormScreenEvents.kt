package tech.toshitworks.attendo.presentation.screen.form_screen
import tech.toshitworks.attendo.domain.model.SubjectModel

sealed class FormScreenEvents {
    data class OnDayClick(val name: String,val add: Boolean) : FormScreenEvents()
    data class OnAddSubjectClick(val subjectModel: SubjectModel): FormScreenEvents()
    data class OnEditSubjectClick(val subjectModel: SubjectModel,val index: Int): FormScreenEvents()
    data class OnRemoveSubjectClick(val subjectModel: SubjectModel): FormScreenEvents()
    data class OnAddPeriodClick(val startTime: String,val endTime: String,val periodDuration: Int): FormScreenEvents()
    data object AddInRoom: FormScreenEvents()
}
