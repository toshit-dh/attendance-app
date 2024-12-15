package tech.toshitworks.attendancechahiye.presentation.components.analysis


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByDay
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByMonth
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByWeek

@Composable
fun AnalysisByTime(
    analyticsByDay: List<AnalyticsByDay>,
    analyticsByWeek: List<AnalyticsByWeek>,
    analyticsByMonth: List<AnalyticsByMonth>
) {
    val pagerState = rememberPagerState {
        3
    }
    HorizontalPager(
        state = pagerState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            when (it) {
                0 -> {
                    Text(
                        text = "Lectures By Day",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    analyticsByDay.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ){
                            Text(text = it.day.name)
                            Text(
                                text = "${it.lecturesPresent} / ${it.lecturesConducted}"
                            )
                        }

                    }
                }

                1 -> {
                    Text(
                        text = "Lectures By Week",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    analyticsByWeek.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(text = it.yearWeek)
                            Text(
                                text = "${it.lecturesPresent} / ${it.lecturesConducted}"
                            )
                        }
                    }
                }

                2 -> {
                    Text(
                        text = "Lectures By Month",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    analyticsByMonth.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(text = it.yearMonth)
                            Text(
                                text = "${it.lecturesPresent} / ${it.lecturesConducted}"
                            )
                        }
                    }
                }
            }
        }
    }
}