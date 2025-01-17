package tech.toshitworks.attendo.presentation.screen.form_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SemesterModel
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.repository.DataStoreRepository
import tech.toshitworks.attendo.domain.repository.DayRepository
import tech.toshitworks.attendo.domain.repository.PeriodRepository
import tech.toshitworks.attendo.domain.repository.SemesterRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import tech.toshitworks.attendo.utils.SnackBarAddEvent
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class FormScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FormScreenStates())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<SnackBarAddEvent>()
    val event = _event.asSharedFlow()
    private fun generateTimeSlots(startTime: String, endTime: String, periodDuration: Int) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val start = LocalTime.parse(startTime, formatter)
        val end = LocalTime.parse(endTime, formatter)
        var currentTime = start
        while (currentTime.isBefore(end)) {
            val periodStartTime = currentTime.format(formatter)
            val periodEndTime = currentTime.plusMinutes(periodDuration.toLong()).format(formatter)
            _state.update {
                it.copy(
                    periodList = it.periodList + PeriodModel(
                        startTime = periodStartTime,
                        endTime = periodEndTime
                    )
                )
            }
            currentTime = currentTime.plusMinutes(periodDuration.toLong())
        }
    }

    private fun getDayOrder(dayName: String): Int {
        return when (dayName) {
            "Monday" -> 1
            "Tuesday" -> 2
            "Wednesday" -> 3
            "Thursday" -> 4
            "Friday" -> 5
            "Saturday" -> 6
            "Sunday" -> 7
            else -> Int.MAX_VALUE
        }
    }

    fun onFormScreen1Events(event: FormScreen1Events){
        when (event) {
            is FormScreen1Events.OnSemesterNumberChange -> {
                _state.update {
                    it.copy(
                        semesterModel = (it.semesterModel ?: SemesterModel(0,event.semNumber, startDate = "")).copy(semNumber = event.semNumber)
                    )
                }
            }

            is FormScreen1Events.OnStartDateChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(startDate = event.startDate)
                    )
                }
            }

            is FormScreen1Events.OnMidTermDateChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(midTermDate = event.midTermDate)
                    )
                }
            }

            is FormScreen1Events.OnEndDateChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(endDate = event.endDate)
                    )
                }
            }
        }

    }


    fun onFormScreen234Event(event: FormScreenEvents) {
        when (event) {
            is FormScreenEvents.OnAddPeriodClick -> {
                _state.update {
                    it.copy(
                        periodList = emptyList()
                    )
                }
                val startTime = event.startTime
                val endTime = event.endTime
                val periodDuration = event.periodDuration
                generateTimeSlots(startTime, endTime, periodDuration)
            }

            is FormScreenEvents.OnAddSubjectClick -> {
                _state.update {
                    val subjectModel = SubjectModel(
                        name = event.subjectModel.name.trim(),
                        facultyName = event.subjectModel.facultyName.trim(),
                        isAttendanceCounted = event.subjectModel.isAttendanceCounted
                    )
                    val isSubjectExists = it.subjectList.any { existingSubject ->
                        existingSubject.name.equals(subjectModel.name, ignoreCase = true)
                    }
                    if (isSubjectExists) {
                        viewModelScope.launch {
                            _event.emit(
                                SnackBarAddEvent.ShowSnackBarForDataNotAdded(
                                    message = "Subject already exists"
                                )
                            )
                        }
                        it
                    } else
                        it.copy(
                            subjectList = it.subjectList + subjectModel
                        )
                }
            }

            is FormScreenEvents.OnRemoveSubjectClick -> {
                _state.update {
                    it.copy(
                        subjectList = it.subjectList.filterNot { subject -> subject == event.subjectModel }
                    )
                }
            }

            is FormScreenEvents.OnDayClick -> {
                if (event.add) {
                    _state.update {
                        it.copy(
                            dayList = (it.dayList + DayModel(name = event.name))
                                .sortedBy { dm ->
                                    getDayOrder(dm.name)
                                }
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            dayList = (it.dayList.filterNot { day -> day.name == event.name })
                                .sortedBy { dm ->
                                    getDayOrder(dm.name)
                                }
                        )
                    }
                }
            }

            FormScreenEvents.AddInRoom -> {
                _state.update {
                    it.copy(
                        subjectList = it.subjectList + SubjectModel(
                            name = "Lunch",
                            facultyName = "null",
                            isAttendanceCounted = false
                        ) + SubjectModel(
                            name = "No Period",
                            facultyName = "null",
                            isAttendanceCounted = false
                        )
                    )
                }
                viewModelScope.launch {
                    try {
                        _event.emit(SnackBarAddEvent.ShowSnackBarForAddingData())
                        semesterRepository.insertSemester(_state.value.semesterModel!!)
                        dayRepository.insertDays(_state.value.dayList)
                        subjectRepository.insertSubjects(_state.value.subjectList)
                        periodRepository.insertPeriods(
                            _state.value.periodList + PeriodModel(
                                startTime = "empty",
                                endTime = "empty"
                            )
                        )
                        dataStoreRepository.saveScreenSelection(2)
                        _event.emit(SnackBarAddEvent.ShowSnackBarForDataAdded())
                    } catch (e: Exception) {
                        _event.emit(SnackBarAddEvent.ShowSnackBarForDataNotAdded())
                        Log.e("Add", e.message ?: "Error adding data")
                    }
                }
            }

            is FormScreenEvents.OnEditSubjectClick -> {
                _state.update {
                    val subjectList = it.subjectList.toMutableList().apply {
                        set(event.index, event.subjectModel)
                    }
                    it.copy(
                        subjectList = subjectList
                    )
                }
            }
        }
    }

}