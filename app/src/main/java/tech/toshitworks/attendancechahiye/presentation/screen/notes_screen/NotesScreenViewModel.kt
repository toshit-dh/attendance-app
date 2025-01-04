package tech.toshitworks.attendancechahiye.presentation.screen.notes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.NoteRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository,
    private val semesterRepository: SemesterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val notes = noteRepository.getNotesByAttendance().reversed()
            val subjects = subjectRepository.getSubjects().filter {
                it.name != "Lunch" && it.name != "No Period"
            }
            val periods = periodRepository.getAllPeriods()
            val days = dayRepository.getDays()
            val semester = semesterRepository.getSemester()
            _state.value = state.value.copy(
                subjects = subjects,
                periods = periods,
                days = days,
                startDate = semester.startDate,
                endDate = semester.endDate,
                notes = notes,
                filteredNotes = notes,
                subjectFilter = subjects.map {
                    it.id
                },
                periodFilter = periods.map {
                    it.id
                },
                dayFilter = days.map {
                    it.id!!
                },
                isLoading = false
            )
        }
    }

    private fun applyFilters() {
        _state.update { currentState ->
            val filteredNotes = _state.value.notes.filter { note ->
                val matchesSubject = currentState.subjectFilter.isEmpty() ||
                        note.attendance.subjectId in currentState.subjectFilter
                val matchesPeriod =
                    currentState.periodFilter.isEmpty() ||
                            note.attendance.periodId in currentState.periodFilter
                val noteDate = LocalDate.parse(note.attendance.date)
                val matchesDate =
                            !noteDate.isBefore(currentState.datesFilter.first) && !noteDate.isAfter(currentState.datesFilter.second)
                val matchesDay =
                    currentState.dayFilter.isEmpty() ||
                            note.attendance.dayId in currentState.dayFilter
                val matchesAttend =
                    (currentState.attend.first && note.attendance.isPresent)
                            || (currentState.attend.second && !note.attendance.isPresent)
                matchesSubject && matchesPeriod && matchesDate && matchesDay && matchesAttend
            }
            currentState.copy(filteredNotes = filteredNotes)
        }
    }

    fun onEvent(event: NotesScreenEvents) {
        when (event) {
            is NotesScreenEvents.OnChangeFilter -> {
                when (val filter = event.filter) {
                    is Filters.Attend -> {
                        _state.update { it.copy(attend = filter.attend) }
                    }

                    is Filters.Date -> {
                        _state.update {
                            it.copy(datesFilter = filter.dates)
                        }
                    }

                    is Filters.Day -> {
                        _state.update {
                            val newDayFilter = if (filter.id in it.dayFilter) {
                                it.dayFilter - filter.id
                            } else {
                                it.dayFilter + filter.id
                            }
                            it.copy(dayFilter = newDayFilter)
                        }
                    }

                    is Filters.Period -> {
                        _state.update {
                            val newPeriodFilter = if (filter.id in it.periodFilter) {
                                it.periodFilter - filter.id
                            } else {
                                it.periodFilter + filter.id
                            }
                            it.copy(periodFilter = newPeriodFilter)
                        }
                    }

                    is Filters.Subject -> {
                        _state.update {
                            val newSubjectFilter = if (filter.id in it.subjectFilter) {
                                it.subjectFilter - filter.id
                            } else {
                                it.subjectFilter + filter.id
                            }
                            it.copy(subjectFilter = newSubjectFilter)
                        }
                    }
                }
                applyFilters()
            }
        }
    }

}