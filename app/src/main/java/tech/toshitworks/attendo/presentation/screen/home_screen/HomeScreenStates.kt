package tech.toshitworks.attendo.presentation.screen.home_screen

import tech.toshitworks.attendo.domain.model.AttendanceBySubject
import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.SubjectModel

data class HomeScreenStates(
    val isLoading: Boolean = true,
    val isAddExtraAttendanceDialogOpen: Boolean = false,
    val isEditAttendanceDatePickerOpen: Boolean = false,
    val isFilterRowVisible: Boolean = true,
    val isSubjectSearchOpen: Boolean = false,
    val attendanceStats: AttendanceStats? = null,
    val attendanceBySubject: List<AttendanceBySubject> = emptyList(),
    val dayList: List<DayModel> = emptyList(),
    val todayDate: String = "",
    val todayDay: String = "",
    val editAttendanceDate: String? = null,
    val subjectList: List<SubjectModel> = emptyList(),
    val analysisSubject: SubjectModel? = null,
    val editList: List<String> = listOf(
        "Semester",
        "Subject",
        "Timetable"
    ),
    val editInfo: Int = 0,
)