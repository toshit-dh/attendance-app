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
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val notes = noteRepository.getNotesByAttendance()
            val subjects = subjectRepository.getSubjects().filter {
                it.name != "Lunch" && it.name != "No Period"
            }

            val periods = periodRepository.getAllPeriods()
            val days = dayRepository.getDays()
            _state.value = state.value.copy(
                subjects = subjects,
                periods = periods,
                days = days,
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
                val matchesDate =
                    currentState.datesFilter.isEmpty() ||
                            note.attendance.date in currentState.datesFilter
                val matchesDay =
                    currentState.dayFilter.isEmpty() ||
                            note.attendance.dayId in currentState.dayFilter
                val matchesAttend =
                    currentState.attend == null ||
                            note.attendance.isPresent == currentState.attend
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
                            val newDatesFilter = if (filter.date in it.datesFilter) {
                                it.datesFilter - filter.date
                            } else {
                                it.datesFilter + filter.date
                            }
                            it.copy(datesFilter = newDatesFilter)
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