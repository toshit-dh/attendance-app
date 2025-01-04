package tech.toshitworks.attendancechahiye.presentation.screen.notes_screen

import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.NotesOf
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import java.time.LocalDate

data class NotesScreenStates(
    val isLoading: Boolean = true,
    val notes: List<NotesOf> = listOf(),
    val filteredNotes: List<NotesOf> = listOf(),
    val subjects: List<SubjectModel> = listOf(),
    val periods: List<PeriodModel> = listOf(),
    val days: List<DayModel> = listOf(),
    val startDate: String = "",
    val endDate: String? = null,
    val subjectFilter: List<Long> = listOf(),
    val periodFilter: List<Long> = listOf(),
    val datesFilter: Pair<LocalDate,LocalDate> = Pair(LocalDate.MIN,LocalDate.MAX),
    val attend: Pair<Boolean,Boolean> = Pair(true,true),
    val dayFilter: List<Long> = listOf()
)
sealed class Filters{
     data class Day(val id: Long): Filters()
     data class Period(val id: Long): Filters()
     data class Subject(val id: Long): Filters()
     data class Date(val dates: Pair<LocalDate,LocalDate>): Filters()
     data class Attend(val attend: Pair<Boolean,Boolean>): Filters()
 }