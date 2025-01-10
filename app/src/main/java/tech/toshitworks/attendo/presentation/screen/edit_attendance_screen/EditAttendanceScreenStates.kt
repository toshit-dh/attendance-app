package tech.toshitworks.attendo.presentation.screen.edit_attendance_screen

import tech.toshitworks.attendo.domain.model.AttendanceBySubject
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.domain.model.NoteModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SubjectModel


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
    val attendanceBySubject: List<AttendanceBySubject> = listOf(),
    val note: NoteModel? = null
)
