package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.DayRepository
import tech.toshitworks.attendancechahiye.domain.repository.PeriodRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val timetableRepository: TimetableRepository,
    private val periodRepository: PeriodRepository,
    private val dayRepository: DayRepository,
    private val subjectRepository: SubjectRepository
): ViewModel(){

    private val _state = MutableStateFlow(EditInfoScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val semesterModel = semesterRepository.getSemester()
            val timetable = timetableRepository.getAllPeriods()
            val periods = periodRepository.getAllPeriods().dropLast(1)
            val days = dayRepository.getDays()
            val subjects = subjectRepository.getSubjects().dropLast(2)
            _state.update {
                it.copy(
                    isLoading = false,
                    semesterModel = semesterModel,
                    timetable = timetable,
                    periodList = periods,
                    dayList = days,
                    subjectList = subjects,
                    listPeriods = timetable
                )
            }
        }
    }

    fun onEvent(event: EditInfoScreenEvents){

    }
}