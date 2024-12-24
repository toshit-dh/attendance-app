package tech.toshitworks.attendancechahiye.presentation.screen.timetable_screen

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
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.domain.repository.DataStoreRepository
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
import tech.toshitworks.attendancechahiye.utils.SnackBarEvent
import javax.inject.Inject

@HiltViewModel
class TimeTableViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {


    private val _state = MutableStateFlow(TimeTableScreenStates())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SnackBarEvent>()
    val event = _event.asSharedFlow()
    init {
        viewModelScope.launch {
            val periods = periodRepository.getPeriods()
            val days = dayRepository.getDays()
            val subjects = subjectRepository.getSubjects()
            _state.update {
                it.copy(
                    dayList = days,
                    periodList = periods,
                    subjectList = subjects,
                    isLoading = false
                )
            }
            val size = (periods.size * days.size)
            val listOfPeriods: List<TimetableModel?> = List(size){
                null
            }
            _state.update {
                it.copy(
                    listPeriods = listOfPeriods
                )
            }
        }

    }



    fun onEvent(event: TimeTableScreenEvents){
        when(event){
            is TimeTableScreenEvents.OnAddPeriod -> {
                _state.update {
                    it.copy(
                        listPeriods = it.listPeriods.toMutableList().apply {
                            set(event.index, event.timetableModel)
                        }
                    )
                }
            }
            TimeTableScreenEvents.OnNextClick -> {
                viewModelScope.launch {
                    try {
                        _event.emit(
                            SnackBarEvent.ShowSnackBarForAddingData()
                        )
                        timetableRepository.insertTimetable(_state.value.listPeriods.filterNotNull())
                        dataStoreRepository.saveScreenSelection(3)
                        _event.emit(
                            SnackBarEvent.ShowSnackBarForDataAdded()
                        )
                    } catch (e: Exception) {
                        Log.e("exception: ",e.message?:"null message")
                        e.printStackTrace()

                    }
                }
            }
        }
    }

}