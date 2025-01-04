package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

sealed class EditInfoScreenEvents{

    data object OnSaveSemester: EditInfoScreenEvents()
    data object OnSaveTimetable: EditInfoScreenEvents()

}