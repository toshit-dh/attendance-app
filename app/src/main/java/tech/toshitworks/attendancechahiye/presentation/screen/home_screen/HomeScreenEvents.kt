package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

sealed class HomeScreenEvents {

    data class OnAddAttendance(val attendanceModel: AttendanceModel): HomeScreenEvents()
    data class OnUpdateAttendance(val attendanceModel: AttendanceModel): HomeScreenEvents()
    data class OnUpdateSubject(val timetableModel: TimetableModel,val index: Int): HomeScreenEvents()
}