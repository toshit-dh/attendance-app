package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import javax.inject.Inject

@HiltViewModel
class EditAttendanceScreenViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
): ViewModel(){

    private val _state = MutableStateFlow(EditAttendanceScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val subjects = subjectRepository.getSubjects()
            val attendance = attendanceRepository.getAllAttendance()
            _state.update {
                it.copy(
                    subjects = subjects,
                    attendance = attendance,
                    isLoading = false
                )
            }
        }
    }

    fun onEvent(event: EditAttendanceScreenEvents){
        when(event){
            is EditAttendanceScreenEvents.OnDateSelected -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }
            is EditAttendanceScreenEvents.OnSubjectSelected -> {
                _state.update {
                    it.copy(
                        selectedSubject = event.subject
                    )
                }
            }
        }
    }
}