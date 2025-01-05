package tech.toshitworks.attendo.presentation.screen.form_screen

sealed class FormScreen1Events {
        data class OnSemesterNumberChange(val semNumber: Int) : FormScreen1Events()
        data class OnStartDateChange(val startDate: String) : FormScreen1Events()
        data class OnMidTermDateChange(val midTermDate: String) : FormScreen1Events()
        data class OnEndDateChange(val endDate: String) : FormScreen1Events()
}