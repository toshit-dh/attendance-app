package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import java.time.LocalDate

data class EditAttendanceScreenStates(
    val isLoading: Boolean = true,
    val date: String? = null,
    val selectedSubject: SubjectModel? = null,
    val subjects: List<SubjectModel> = listOf(),
    val attendance: List<AttendanceModel> = listOf()
)
