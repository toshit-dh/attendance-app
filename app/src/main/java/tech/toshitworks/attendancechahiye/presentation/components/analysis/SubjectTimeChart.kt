package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsModel
import tech.toshitworks.attendancechahiye.utils.colors

@Composable
fun SubjectTimeChart(
    isOverall: Boolean,
   analyticsModel: AnalyticsModel,
   subjectAnalyticsModel: List<AnalyticsModel>,
){

    val slices = subjectAnalyticsModel.mapIndexed {idx,it->
        PieChartData.Slice(
            it.subject!!.name,
            it.lecturesPresent.toFloat(),
            colors[(idx) % colors.size]
        )
    }
    val pieChartConfig = PieChartConfig(
        labelVisible = true,
        isAnimationEnable = true,
        showSliceLabels = true,
        sliceLabelTextColor = Color.DarkGray,
        animationDuration = 1500,
        backgroundColor = CardDefaults.cardColors().containerColor
    )
    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie,
    )
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Column (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnalysisByTime(
                    analyticsByDay = analyticsModel.analyticsByDay,
                    analyticsByWeek = analyticsModel.analysisByWeek,
                    analyticsByMonth = analyticsModel.analysisByMonth
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isOverall)
                    Card {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center
                        ){
                            PieChart(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                pieChartData,
                                pieChartConfig,
                            )
                        }
                    }
                else{
                    Card  {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = "Streak",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Current:  5 🎯"
                            )
                            Text(
                                text = "Longest:  8 🎯"
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .clickable {

                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.background
                                    ),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(6.dp)
                                    ) {
                                        Text(
                                            text = "View Streak"
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
}