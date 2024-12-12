package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import tech.toshitworks.attendancechahiye.domain.model.SemesterModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

data class EditInfoScreenStates(
    val isLoading: Boolean = true,
    val semesterModel: SemesterModel? = null,
    val timetable: List<TimetableModel> = listOf()
)
