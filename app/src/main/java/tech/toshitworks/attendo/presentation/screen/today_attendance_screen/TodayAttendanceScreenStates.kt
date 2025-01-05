package tech.toshitworks.attendo.presentation.screen.today_attendance_screen

import tech.toshitworks.attendo.domain.model.AttendanceBySubject
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.NoteModel
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.model.TimetableModel

data class TodayAttendanceScreenStates(
    val isLoading: Boolean = true,
    val day: DayModel? = null,
    val date: String = "",
    val startDate: String = "",
    val endDate: String? = "",
    val dayList: List<DayModel> = listOf(),
    val subjectList: List<SubjectModel> = listOf(),
    val attendanceByDate: List<AttendanceModel> = listOf(),
    val attendanceList: List<AttendanceBySubject> = listOf(),
    val timetableForDay: List<TimetableModel> = listOf(),
    val attendanceStats: AttendanceStats = AttendanceStats(0, 0, 0.0),
    val notes: List<NoteModel> = listOf(),
    val noteModel: NoteModel? = null
)