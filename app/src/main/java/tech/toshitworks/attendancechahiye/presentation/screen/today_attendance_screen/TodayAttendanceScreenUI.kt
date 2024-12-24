package tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.presentation.components.today_attendance.TimetableForDay
import java.time.LocalDate


@Composable
fun TodayAttendanceScreen(
    modifier: Modifier = Modifier,
    viewModel: TodayAttendanceScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    var endDateNull = false
    if (!state.isLoading) {
        val startDate = state.startDate.split("-")
        val todayDate = state.date.split("-")
        val endDate = state.endDate?.split("-")
        val sdLocale = LocalDate.of(startDate[0].toInt(), startDate[1].toInt(), startDate[2].toInt())
        val tdLocale = LocalDate.of(todayDate[0].toInt(), todayDate[1].toInt(), todayDate[2].toInt())
        val edLocale = try{
            LocalDate.of(endDate?.get(0)?.toInt() ?: 0, endDate?.get(1)?.toInt() ?: 0, endDate?.get(2)?.toInt() ?: 0)
        }catch (e: Exception){
            endDateNull = true
            LocalDate.of(2000,1,1)
        }
        Column(
            modifier = modifier
        ) {
            val isValidDay = state.day != null && state.dayList.contains(state.day)
            val isInDateRange = tdLocale.isAfter(sdLocale) ||
                    (tdLocale.isEqual(sdLocale) && (endDateNull || tdLocale.isBefore(edLocale)))
            if (isValidDay && isInDateRange) {
                TimetableForDay(
                    state = state,
                    onEvent = onEvent,
                    date = state.date,
                    day = state.day!!
                )
            }
        }
    }
}


