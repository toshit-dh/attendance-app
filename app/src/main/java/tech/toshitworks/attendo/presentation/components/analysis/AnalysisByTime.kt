package tech.toshitworks.attendo.presentation.components.analysis


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.AnalyticsByDay
import tech.toshitworks.attendo.domain.model.AnalyticsByMonth
import tech.toshitworks.attendo.domain.model.AnalyticsByWeek
import tech.toshitworks.attendo.utils.colors

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
        Card {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                when (it) {
                    0 -> {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Lectures By Day",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(analyticsByDay) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween

                                ) {
                                    val color = colors().random()
                                    Text(
                                        text = it.day.name,
                                        color = color
                                    )
                                    Text(
                                        text = "${it.lecturesPresent} / ${it.lecturesConducted}",
                                        color = color
                                    )
                                }
                            }

                        }
                    }

                    1 -> {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Lectures By Week",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(analyticsByWeek) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val color = colors().random()
                                    Text(
                                        text = it.yearWeek,
                                        color = color
                                    )
                                    Text(
                                        text = "${it.lecturesPresent} / ${it.lecturesConducted}",
                                        color = color
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Lectures By Month",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(analyticsByMonth) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val color = colors().random()
                                    Text(
                                        text = it.yearMonth,
                                        color = color
                                    )
                                    Text(
                                        text = "${it.lecturesPresent} / ${it.lecturesConducted}",
                                        color = color
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}