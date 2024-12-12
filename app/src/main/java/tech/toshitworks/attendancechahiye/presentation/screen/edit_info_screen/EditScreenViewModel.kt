package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.TimetableRepository
import tech.toshitworks.attendancechahiye.presentation.screen.form_screen.FormScreen1Events
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val timetableRepository: TimetableRepository
): ViewModel(){

    private val _state = MutableStateFlow(EditInfoScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val semesterModel = semesterRepository.getSemester()
            val timetable = timetableRepository.getAllPeriods()
            _state.update {
                it.copy(
                    isLoading = false,
                    semesterModel = semesterModel,
                    timetable = timetable
                )
            }
        }
    }

    fun onEvent(event: FormScreen1Events){
        when(event){
            is FormScreen1Events.OnEndDateChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(
                            endDate = event.endDate
                        )
                    )
                }
            }
            is FormScreen1Events.OnMidTermDateChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(
                            midTermDate = event.midTermDate
                        )
                    )
                }
            }
            is FormScreen1Events.OnSemesterNumberChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(
                            semNumber = event.semNumber
                        )
                    )
                }
            }
            is FormScreen1Events.OnStartDateChange -> {
                _state.update {
                    it.copy(
                        semesterModel = it.semesterModel?.copy(
                            startDate = event.startDate
                        )
                    )
                }
            }
        }
    }
}