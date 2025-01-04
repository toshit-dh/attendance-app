package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import tech.toshitworks.attendancechahiye.domain.model.AttendanceBySubject
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.AttendanceStats
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel


data class EditAttendanceScreenStates(
    val isLoading: Boolean = true,
    val date: String? = null,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val selectedSubject: SubjectModel? = null,
    val subjects: List<SubjectModel> = listOf(),
    val periods: List<PeriodModel> = listOf(),
    val attendanceStats: AttendanceStats = AttendanceStats(0,0,0.0),
    val filteredAttendance: List<AttendanceModel> = listOf(),
    val attendance: List<AttendanceModel> = listOf(),
    val attendanceBySubject: List<AttendanceBySubject> = listOf()
)
