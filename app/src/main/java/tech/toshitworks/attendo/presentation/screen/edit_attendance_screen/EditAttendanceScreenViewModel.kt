package tech.toshitworks.attendo.presentation.screen.edit_attendance_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.PeriodRepository
import tech.toshitworks.attendo.domain.repository.SemesterRepository
import tech.toshitworks.attendo.domain.repository.SubjectRepository
import tech.toshitworks.attendo.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EditAttendanceScreenViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository,
    private val subjectRepository: SubjectRepository,
    private val periodRepository: PeriodRepository,
    private val attendanceRepository: AttendanceRepository
): ViewModel(){

    private val _state = MutableStateFlow(EditAttendanceScreenStates())
    val state = combine(
        _state,
        attendanceRepository.getPlusDeletedAttendance(),
        attendanceRepository.getOverallAttendance(),
        attendanceRepository.getAttendancePercentage(),
    ){
        state,attendance,attendanceBySubject,stats ->
        state.copy(
            attendance = attendance,
            filteredAttendance = attendance.filter { am->
            am.date == _state.value.date
        },
            attendanceBySubject = attendanceBySubject,
            attendanceStats = stats
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EditAttendanceScreenStates()
    )

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
            val subjects = subjectRepository.getSubjects().filter {
                it.name != "Lunch" && it.name != "No Period" && it.isAttendanceCounted
            }
            val periods = periodRepository.getPeriods()
            _state.update {
                it.copy(
                    subjects = subjects,
                    periods = periods,
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
                    val list = state.value.attendance.filter { am->
                        am.date == event.date
                    }
                    it.copy(
                        date = event.date,
                        filteredAttendance = list
                    )
                }
            }

            is EditAttendanceScreenEvents.OnAddExtraAttendance -> {
                viewModelScope.launch {
                        attendanceRepository.insertAttendance(event.attendanceModel)
                }

            }
        }
    }
    fun attendanceEvent(event: TodayAttendanceScreenEvents){
        when(event){
            is TodayAttendanceScreenEvents.OnAddAttendance -> {}
            is TodayAttendanceScreenEvents.OnAddNote -> {}
            is TodayAttendanceScreenEvents.OnDeletePeriod -> {
                viewModelScope.launch {
                    attendanceRepository.updateAttendanceByDate(event.attendanceModel)
                }
            }
            is TodayAttendanceScreenEvents.OnUpdateAttendance -> {
                viewModelScope.launch {
                    attendanceRepository.updateAttendanceByDate(event.attendanceModel)
                }
            }
            is TodayAttendanceScreenEvents.OnUpdatePeriod -> {}
        }
    }
}