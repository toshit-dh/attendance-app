package tech.toshitworks.attendancechahiye.presentation.screen.notes_screen

import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.NotesOf
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.Six
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

data class NotesScreenStates(
    val notes: List<NotesOf> = listOf(),
    val subjects: List<SubjectModel> = listOf(),
    val periods: List<PeriodModel> = listOf(),
    val days: List<DayModel> = listOf(),
    val subjectFilter: List<Long> = listOf(),
    val periodFilter: List<Long> = listOf(),
    val datesFilter: List<String> = listOf(),
    val attend: Boolean? = null,
    val dayFilter: List<Long> = listOf()
)
sealed class Filters{
     data class Day(val id: Long): Filters()
     data class Period(val id: Long): Filters()
     data class Subject(val id: Long): Filters()
     data class Date(val date: String): Filters()
     data class Attend(val attend: Boolean): Filters()
 }