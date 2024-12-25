package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsModel
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.AttendanceStats
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.PickDateDialog
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreenState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import kotlin.reflect.KFunction2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisForSubject(
    state: AnalyticsScreenState,
    onEvent: (AnalyticsScreenEvents) -> Unit,
    analyticsModel: AnalyticsModel,
    subjectAnalysis: List<AnalyticsModel>,
    getWeek: KFunction2<Int, Int, Pair<LocalDate, LocalDate>>,
    attendanceList: List<AttendanceModel>
) {
    val calendar = Calendar.getInstance()
    val isOverall = analyticsModel.subject == null
    val attendanceListFiltered = if (!isOverall) attendanceList.filter {
        it.subject == analyticsModel.subject
    }.reversed() else attendanceList.reversed()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isSheetOpen by remember {
        mutableStateOf(false)
    }
    val isDatePickerOpen = remember {
        mutableStateOf(Pair(false,""))
    }
    val fromDate: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    val toDate: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    val attendanceStats: MutableState<AttendanceStats?> = remember {
        mutableStateOf(null)
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(if (isOverall) 6f else 4f)
        ) {
            SubjectTimeChart(
                isOverall = isOverall,
                analyticsModel = analyticsModel,
                subjectAnalyticsModel = subjectAnalysis
            )
        }
        if (!isOverall)
            Box(
                modifier = Modifier
                    .weight(5f)
            ) {
                if (analyticsModel.subject?.isAttendanceCounted == true)
                    EligibilityAnalysis(
                        analyticsModel.eligibilityOfMidterm,
                        analyticsModel.eligibilityOfEndSem
                    )
                else
                    Box {

                    }
            }
        else
            Box(
                modifier = Modifier
                    .weight(3f)
            ) {

            }
        Box(
            modifier = Modifier
                .weight(9f)
        ) {
            if (analyticsModel.analysisByWeek.isNotEmpty())
                TrendAnalysis(
                    getWeek = getWeek,
                    analyticsByWeek = analyticsModel.analysisByWeek
                )
        }
        Card(
            modifier = Modifier
                .weight(1.2f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (fromDate.value != null) fromDate.value!! else "From Date: ?",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    IconButton(
                        onClick = {
                            isDatePickerOpen.value = Pair(true,"from")

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "view sheet"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (toDate.value != null) toDate.value!! else "To Date: ?",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    IconButton(
                        onClick = {
                            isDatePickerOpen.value = Pair(true,"to")

                        },
                        enabled = fromDate.value != null
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "view sheet"
                        )
                    }
                }
                val aS = attendanceStats.value == null
                val totalPresent = if (aS) "?" else attendanceStats.value!!.totalPresent.toString()
                val totalLectures =
                    if (aS) "?" else attendanceStats.value!!.totalLectures.toString()
                val percentage = if (totalPresent == "?") "?"
                else String.format(Locale.US, "%.2f", attendanceStats.value!!.attendancePercentage)
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = "$totalPresent/$totalLectures - $percentage%",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "View List"
                )
                IconButton(
                    onClick = {
                        scope.launch {
                            if (!isSheetOpen) {
                                isSheetOpen = true
                                sheetState.show()
                            } else {
                                isSheetOpen = false
                                sheetState.hide()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = "view sheet"
                    )
                }
            }
        }
        AttendanceList(
            isSheetOpen = isSheetOpen,
            subject = analyticsModel.subject,
            attendanceList = attendanceListFiltered,
            sheetState = sheetState,
        ) {
            scope.launch {
                isSheetOpen = false
                sheetState.hide()
            }
        }
        if (isDatePickerOpen.value.first) {
            val isFrom = isDatePickerOpen.value.second == "from"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val minDate = if (isFrom) state.startDate else dateFormat.parse(fromDate.value!!)?.time ?: 0L
            val today = calendar.timeInMillis
            val endDate = state.endDate
            val maxDate = if (isFrom) if (toDate.value==null) if (today > endDate) state.endDate else today else dateFormat.parse(toDate.value!!)?.time ?: 0L else today
            PickDateDialog(
                minDate = minDate,
                maxDate = maxDate
            ) {date->
                if (isDatePickerOpen.value.second=="from") fromDate.value = date else if (isDatePickerOpen.value.second=="to") toDate.value = date
                if (fromDate.value != null && toDate.value != null){
                    onEvent(AnalyticsScreenEvents.OnDateRangeAttendance(
                        analyticsModel.subject,
                        fromDate.value!!,
                        toDate.value!!,
                    ) {
                        attendanceStats.value = it
                    }
                    )
                }
                isDatePickerOpen.value = Pair(false,"")
            }
        }
    }
}
