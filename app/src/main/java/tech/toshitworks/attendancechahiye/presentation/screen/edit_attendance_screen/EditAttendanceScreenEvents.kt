package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

sealed class EditAttendanceScreenEvents {

    data class OnDateSelected(val date: String): EditAttendanceScreenEvents()

}