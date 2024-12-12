package tech.toshitworks.attendancechahiye.presentation.screen.notes_screen

sealed class NotesScreenEvents {
    data class OnChangeFilter(val filter: Filters): NotesScreenEvents()
}