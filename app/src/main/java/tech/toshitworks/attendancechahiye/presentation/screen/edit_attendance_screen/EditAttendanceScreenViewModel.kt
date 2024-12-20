package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import tech.toshitworks.attendancechahiye.domain.repository.SubjectRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EditAttendanceScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
): ViewModel(){

    private val _state = MutableStateFlow(EditAttendanceScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val semester = semesterRepository.getSemester()
            val minDateString = semester.startDate
            val maxDateString = semester.endDate
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val minDate = dateFormat.parse(minDateString)?.time ?: 0L
            val maxDate = maxDateString?.let {
                try {
                    dateFormat.parse(it)?.time
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            } ?: 0L
            val subjects = subjectRepository.getSubjects()
            val attendance = attendanceRepository.getAllAttendance()
            _state.update {
                it.copy(
                    subjects = subjects,
                    attendance = attendance,
                    isLoading = false,
                    startDate = minDate,
                    endDate = maxDate
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
        }
    }
}