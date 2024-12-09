package tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.presentation.components.analysis.EligibilityAnalysis
import tech.toshitworks.attendancechahiye.presentation.components.analysis.SubjectTimeChart
import tech.toshitworks.attendancechahiye.presentation.components.analysis.TrendAnalysis
import java.util.Locale

@Composable
fun AnalyticsScreen(
    modifier: Modifier,
    viewModel: AnalyticsScreenViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val getWeek = viewModel::getWeekDateRange
    val pageCount = state.analyticsList.size
    val analysisList = state.analyticsList
    val pagerState = rememberPagerState { pageCount }
    val maxIndicators = 5

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
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                        if (analysisList[it].subject?.isAttendanceCounted == true)
                            EligibilityAnalysis(
                                analysisList[it].eligibilityOfMidterm,
                                analysisList[it].eligibilityOfEndSem
                            )
                        TrendAnalysis(
                            getWeek = getWeek,
                            analyticsByWeek = analysisList[it].analysisByWeek
                        )
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
}
