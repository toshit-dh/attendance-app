package tech.toshitworks.attendo.presentation.screen.analytics_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.domain.repository.AnalyticsRepository
import tech.toshitworks.attendo.domain.repository.AttendanceRepository
import tech.toshitworks.attendo.domain.repository.SemesterRepository
import java.text.SimpleDateFormat
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
            val analyticList = async {
                analyticsRepository.getAnalysis(
                    semester.startDate,
                    semester.midTermDate,
                    semester.endDate
                )
            }
            val attendance = async { attendanceRepository.getAllAttendance().first() }
            _state.update {
                it.copy(
                    isLoading = false,
                    analyticsList = analyticList.await(),
                    attendanceList = attendance.await(),
                    filteredAttendanceList = attendance.await(),
                    startDate = minDate,
                    endDate = maxDate
                )
            }
        }
    }

    fun onEvent(event: AnalyticsScreenEvents) {
        when (event) {
            is AnalyticsScreenEvents.OnDateRangeAttendance -> {
                val filteredAttendance = _state.value.attendanceList.filter {
                    val subject =
                        if (event.subjectModel == null) true else it.subject == event.subjectModel
                    val itDate = LocalDate.parse(it.date)
                    val fromDateParsed = LocalDate.parse(event.fromDate)
                    val toDateParsed = LocalDate.parse(event.toDate)
                    subject && itDate in fromDateParsed..toDateParsed
                }.fold(AttendanceStats(0, 0, 0.0)) { acc: AttendanceStats, attendanceModel ->

                    val subject = event.subjectModel == null
                    val totalLectures =
                        if (subject)
                            if (attendanceModel.subject?.isAttendanceCounted == true) acc.totalLectures + 1 else acc.totalLectures
                        else acc.totalLectures + 1
                    val totalPresent =
                        if (subject)
                            if (attendanceModel.subject?.isAttendanceCounted == true) if (attendanceModel.isPresent) acc.totalPresent + 1 else acc.totalPresent else acc.totalPresent
                        else if (attendanceModel.isPresent) acc.totalPresent + 1 else acc.totalPresent
                    AttendanceStats(
                        totalPresent = totalPresent,
                        totalLectures = totalLectures,
                        attendancePercentage = (acc.totalPresent.toDouble() / acc.totalLectures.toDouble()) * 100.0
                    )
                }
                event.stats(filteredAttendance)
            }
        }
    }

}
