package tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen

import CircularProgress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.ChangeSubjectDialog
import tech.toshitworks.attendancechahiye.presentation.components.home.TimetableForDay
import java.time.LocalDate


@Composable
fun TodayAttendanceScreen(
    modifier: Modifier = Modifier,
    viewModel: TodayAttendanceScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val isSubjectChangedDialogOpen = remember {
        mutableStateOf(false)
    }
    var endDateNull: Boolean = false
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .padding(12.dp),
            ) {
                Text(
                    text = "Overall Attendance",
                    modifier = Modifier.padding(8.dp, 3.dp),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${state.attendanceStats.totalPresent} / ${state.attendanceStats.totalLectures}",
                        fontSize = 20.sp
                    )
                    CircularProgress(
                        modifier = Modifier.weight(1f),
                        percentage = state.attendanceStats.attendancePercentage.toFloat()
                    )
                }
            }
            val isValidDay = state.day != null && state.dayList.contains(state.day)
            val isInDateRange = tdLocale.isAfter(sdLocale) ||
                    (tdLocale.isEqual(sdLocale) && (endDateNull || tdLocale.isBefore(edLocale)))
            if (isValidDay && isInDateRange) {
                TimetableForDay(
                    state = state,
                    onEvent = onEvent,
                    onEditIconClick = {
                        isSubjectChangedDialogOpen.value = true
                    },
                    date = state.date,
                    day = state.day!!
                )
                if (isSubjectChangedDialogOpen.value)
                    ChangeSubjectDialog(
                        subjects = state.subjectList,
                        onDismiss = {
                            isSubjectChangedDialogOpen.value = false
                        }
                    ) { a, b ->

                    }
            }
        }
    }
}


