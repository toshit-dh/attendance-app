package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import tech.toshitworks.attendancechahiye.domain.model.AttendanceBySubject
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.AttendanceStats
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

data class HomeScreenStates(
    val isLoading: Boolean = true,
    val dayList: List<String> = listOf(),
    val subjectList: List<SubjectModel> = listOf(),
    val attendanceByDate: List<AttendanceModel> = listOf(),
    val attendanceList: List<AttendanceBySubject> = listOf(),
    val timetableForDay: List<TimetableModel> = listOf(),
    val attendanceStats: AttendanceStats = AttendanceStats(0, 0, 0.0)
)