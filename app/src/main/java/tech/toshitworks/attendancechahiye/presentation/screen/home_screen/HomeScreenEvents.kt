package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

sealed class HomeScreenEvents {

    data object OnEditAttendanceCalendarClick: HomeScreenEvents()
    data class OnEditAttendanceDateSelected(val date: String): HomeScreenEvents()
    data class OnEditTypeChange(val change: Int): HomeScreenEvents()
    data object OnAddExtraAttendanceClick: HomeScreenEvents()
    data object OnSearchSubject: HomeScreenEvents()
    data class OnSubjectSelectForAnalysis(val subjectModel: SubjectModel?): HomeScreenEvents()
    data class OnExportClick(val tables: List<String>): HomeScreenEvents()
}