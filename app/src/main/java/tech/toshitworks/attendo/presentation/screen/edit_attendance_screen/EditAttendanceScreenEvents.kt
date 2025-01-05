package tech.toshitworks.attendo.presentation.screen.edit_attendance_screen

import tech.toshitworks.attendo.domain.model.AttendanceModel

sealed class EditAttendanceScreenEvents  {

    data class OnDateSelected(val date: String) : EditAttendanceScreenEvents()
    data class OnAddExtraAttendance(val attendanceModel: AttendanceModel) : EditAttendanceScreenEvents()
}