package tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.presentation.components.analysis.AttendanceList
import tech.toshitworks.attendancechahiye.presentation.components.analysis.SubjectTimeChart
import tech.toshitworks.attendancechahiye.presentation.components.analysis.TrendAnalysis
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    modifier: Modifier,
    viewModel: AnalyticsScreenViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val getWeek = viewModel::getWeekDateRange
    val pageCount = state.analyticsList.size
    val analysisList = state.analyticsList
    val pagerState = rememberPagerState { pageCount }
    val maxIndicators = 5
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isSheetOpen by remember {
        mutableStateOf(false)
    }
    var subject: SubjectModel? by remember {
        mutableStateOf(null)
    }
    if (!state.isLoading)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                state = pagerState,
                userScrollEnabled = true
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = " ${analysisList[it].subject?.name ?: "Overall "} Analysis: ${analysisList[it].lecturesPresent} / ${analysisList[it].lecturesConducted}: ${
                                String.format(
                                    Locale.US,
                                    "%.2f",
                                    (analysisList[it].lecturesPresent.toFloat() / analysisList[it].lecturesConducted.toFloat()) * 100
                                )
                            } %",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        SubjectTimeChart(
                            page = it,
                            analyticsModel = analysisList[it],
                            subjectAnalyticsModel = analysisList.drop(1).filter {
                                it.subject!!.isAttendanceCounted
                            }
                        )
                        if (analysisList[it].analysisByWeek.isNotEmpty())
                            TrendAnalysis(
                                getWeek = getWeek,
                                analyticsByWeek = analysisList[it].analysisByWeek
                            )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
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
                                                subject =
                                                    if (it == 0) null else analysisList[it].subject
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
                    }
                }

            }
            AttendanceList(
                onEvent = onEvent,
                isSheetOpen = isSheetOpen,
                subject = subject,
                attendanceList = state.filteredAttendanceList,
                sheetState = sheetState
            ) {
                scope.launch {
                    isSheetOpen = false
                    sheetState.hide()
                }
            }
        }
    Row(
        Modifier
            .height(25.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val visibleRange = when {
            pageCount <= maxIndicators -> 0 until pageCount
            pagerState.currentPage < maxIndicators / 2 -> 0 until maxIndicators
            pagerState.currentPage > pageCount - maxIndicators / 2 -> (pageCount - maxIndicators) until pageCount
            else -> (pagerState.currentPage - maxIndicators / 2)..(pagerState.currentPage + maxIndicators / 2)
        }

        for (i in visibleRange) {
            val color = if (pagerState.currentPage == i) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = color,
                        shape = if (pagerState.currentPage == i) RoundedCornerShape(0.dp) else CircleShape
                    )
                    .size(10.dp)
            )
        }
    }
}

