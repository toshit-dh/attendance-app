package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
import tech.toshitworks.attendancechahiye.utils.SnackBarEditEvent
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val timetableRepository: TimetableRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EditInfoScreenStates())
    val state = _state.asStateFlow()

    private val _snackBarEvent = MutableSharedFlow<SnackBarEditEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val semesterModel = semesterRepository.getSemester()
            val timetable = timetableRepository.getAllPeriods()
            val periods = periodRepository.getAllPeriods().filter {
                it.startTime != "empty" && it.endTime != "empty"
            }
            val days = dayRepository.getDays()
            val subjects = subjectRepository.getSubjects().filter {
                it.name != "Lunch" && it.name != "No Period"
            }
            _state.update {
                it.copy(
                    isLoading = false,
                    semesterModel = semesterModel,
                    changedSemesterModel = semesterModel,
                    periodList = periods,
                    listPeriods = timetable,
                    dayList = days,
                    subjectList = subjects,
                    changedSubjectList = subjects,
                    timetable = timetable,
                    changedTimetable = timetable
                )
            }
        }
    }

    fun onEvent(event: EditInfoScreenEvents) {
        when (event) {
            EditInfoScreenEvents.OnSaveSemester -> {
                viewModelScope.launch {
                    if (_state.value.semesterModel!! == _state.value.changedSemesterModel) {
                        println(_state.value.semesterModel)
                        println(_state.value.changedSemesterModel)
                        println(_state.value.semesterModel == _state.value.changedSemesterModel)
                        _snackBarEvent.emit(
                            SnackBarEditEvent.ShowSnackBarForNoChange()
                        )
                    }
                    else {
                        semesterRepository.insertSemester(_state.value.semesterModel!!)
                        _snackBarEvent.emit(SnackBarEditEvent.ShowSnackBarForDataEdited())
                        _state.update {
                            it.copy(
                                changedSemesterModel = _state.value.semesterModel
                            )
                        }
                    }
                }
            }

            EditInfoScreenEvents.OnSaveTimetable -> {
                viewModelScope.launch {

                }
            }
        }
    }
}