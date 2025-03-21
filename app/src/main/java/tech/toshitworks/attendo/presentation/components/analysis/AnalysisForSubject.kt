package tech.toshitworks.attendo.presentation.components.analysis

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.domain.model.AnalyticsModel
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.AttendanceStats
import tech.toshitworks.attendo.presentation.components.dialogs.PickDateDialog
import tech.toshitworks.attendo.presentation.screen.analytics_screen.AnalyticsScreenEvents
import tech.toshitworks.attendo.presentation.screen.analytics_screen.AnalyticsScreenState
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenEvents
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import kotlin.reflect.KFunction2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisForSubject(
    state: AnalyticsScreenState,
    homeScreenEvents: (HomeScreenEvents) -> Unit,
    onEvent: (AnalyticsScreenEvents) -> Unit,
    analyticsModel: AnalyticsModel,
    subjectAnalysis: List<AnalyticsModel>,
    getWeek: KFunction2<Int, Int, Pair<LocalDate, LocalDate>>,
    attendanceList: List<AttendanceModel>,
    onNavigation: () -> Unit
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
        mutableStateOf(Pair(false, ""))
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
    if (analyticsModel.lecturesConducted == 0) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.analysis),
                contentDescription = "no data"
            )
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "No data for analysis. Add some attendance first.",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Justify
            )
        }
        return
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        SubjectTimeChart(
            modifier = Modifier
                .weight(6f),
            isOverall = isOverall,
            analyticsModel = analyticsModel,
            subjectAnalyticsModel = subjectAnalysis,
        ) {
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
        if (!isOverall)
            Card(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxSize()
            ) {
                if (analyticsModel.subject?.isAttendanceCounted == true)
                    EligibilityAnalysis(
                        analyticsModel.eligibilityOfMidterm,
                        analyticsModel.eligibilityOfEndSem
                    )
                else
                    NoAttendanceSubject(homeScreenEvents, onNavigation)
            }
        else
            PeriodAnalysis(
                modifier = Modifier
                    .weight(3f),
                analyticsModel.periodAnalysis!!
            )
        AttendanceByDateRange(
            modifier = Modifier
                .weight(1.5f),
            fromDate,
            isDatePickerOpen,
            toDate,
            attendanceStats
        )
        TrendAnalysis(
            modifier = Modifier
                .weight(if (!isOverall) 7.5f else 9.5f),
            getWeek = getWeek,
            analyticsByWeek = analyticsModel.analysisByWeek
        )
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
            val minDate =
                if (isFrom) state.startDate else dateFormat.parse(fromDate.value!!)?.time ?: 0L
            val today = calendar.timeInMillis
            val endDate = state.endDate
            val maxDate =
                if (isFrom) if (toDate.value == null) if (today > endDate) state.endDate else today else dateFormat.parse(
                    toDate.value!!
                )?.time ?: 0L else today
            PickDateDialog(
                minDate = minDate,
                maxDate = maxDate
            ) { date ->
                if (isDatePickerOpen.value.second == "from") fromDate.value =
                    date else if (isDatePickerOpen.value.second == "to") toDate.value = date
                if (fromDate.value != null && toDate.value != null) {
                    onEvent(AnalyticsScreenEvents.OnDateRangeAttendance(
                        analyticsModel.subject,
                        fromDate.value!!,
                        toDate.value!!,
                    ) {
                        attendanceStats.value = it
                    }
                    )
                }
                isDatePickerOpen.value = Pair(false, "")
            }
        }
    }
}