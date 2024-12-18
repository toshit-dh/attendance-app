package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

sealed class EditAttendanceScreenEvents {

    data class OnDateSelected(val date: String): EditAttendanceScreenEvents()
    data class OnSubjectSelected(val subject: SubjectModel): EditAttendanceScreenEvents()

}