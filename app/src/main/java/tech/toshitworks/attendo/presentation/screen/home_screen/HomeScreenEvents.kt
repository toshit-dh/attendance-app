package tech.toshitworks.attendo.presentation.screen.home_screen

import tech.toshitworks.attendo.domain.model.SubjectModel

sealed class HomeScreenEvents {

    data object OnEditAttendanceCalendarClick: HomeScreenEvents()
    data class OnEditAttendanceDateSelected(val date: String?): HomeScreenEvents()
    data class OnEditTypeChange(val change: Int): HomeScreenEvents()
    data object OnAddExtraAttendanceClick: HomeScreenEvents()
    data object OnNoteFilterRowVisibilityChange: HomeScreenEvents()
    data object OnSearchSubject: HomeScreenEvents()
    data class OnSubjectSelectForAnalysis(val subjectModel: SubjectModel?): HomeScreenEvents()
}