package tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.repository.AnalyticsRepository
import tech.toshitworks.attendancechahiye.domain.repository.AttendanceRepository
import tech.toshitworks.attendancechahiye.domain.repository.SemesterRepository
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AnalyticsScreenViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository,
    private val attendanceRepository: AttendanceRepository,
    private val semesterRepository: SemesterRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AnalyticsScreenState())
    val state = _state.asStateFlow()

    fun getWeekDateRange(year: Int, weekNumber: Int): Pair<LocalDate, LocalDate> {
        val firstDayOfYear = LocalDate.of(year, 1, 1)
        val weekFields = WeekFields.of(Locale.getDefault())
        val startOfWeek = firstDayOfYear.with(weekFields.weekOfYear(), weekNumber.toLong())
            .with(weekFields.dayOfWeek(), 1L)
        val endOfWeek = startOfWeek.plusDays(6)
        return Pair(startOfWeek, endOfWeek)
    }

    init {
        viewModelScope.launch {
            val info = semesterRepository.getSemester()
            val attendance = attendanceRepository.getAllAttendance()
            val analyticList =
                analyticsRepository.getAnalysis(info.startDate, info.midTermDate, info.endDate)
            _state.update {
                it.copy(
                    isLoading = false,
                    analyticsList = analyticList,
                    attendanceList = attendance,
                    filteredAttendanceList = attendance
                )
            }
        }
    }

    fun onEvent(event: AnalyticsScreenEvents) {
        when (event) {
            is AnalyticsScreenEvents.OnChangeSubject -> {
                val filteredList = if (event.subjectId!=0L)_state.value.attendanceList.filter {
                    it.subject!!.id == event.subjectId
                } else _state.value.attendanceList
                _state.update {
                    it.copy(
                        filteredAttendanceList = filteredList
                    )
                }
            }
        }
    }

}
