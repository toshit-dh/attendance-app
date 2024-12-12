package tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen

import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

sealed class TodayAttendanceScreenEvents {

    data class OnAddAttendance(val attendanceModel: AttendanceModel): TodayAttendanceScreenEvents()
    data class OnUpdateAttendance(val attendanceModel: AttendanceModel): TodayAttendanceScreenEvents()
    data class OnUpdateSubject(val timetableModel: TimetableModel,val index: Int): TodayAttendanceScreenEvents()
    data class OnAddNote(val noteModel: NoteModel): TodayAttendanceScreenEvents()
}