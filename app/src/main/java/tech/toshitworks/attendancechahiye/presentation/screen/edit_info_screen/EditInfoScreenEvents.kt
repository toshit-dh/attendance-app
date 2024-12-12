package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import tech.toshitworks.attendancechahiye.domain.model.SemesterModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

sealed class EditInfoScreenEvents{

    data class OnChangeSemester(val semesterModel: SemesterModel): EditInfoScreenEvents()
    data object OnSaveSemester: EditInfoScreenEvents()

    data class OnChangeTimetable(val index: Int,val timetableModel: TimetableModel): EditInfoScreenEvents()
    data object OnSaveTimetable: EditInfoScreenEvents()

}