package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val streakQuotes = listOf(
        "Rock bottom. Start climbing.",
        "Every streak starts with one.",
        "Consistency builds strength.",
        "Momentum is building—keep it up!",
        "Laying strong foundations.",
        "Halfway to greatness!",
        "Discipline is turning into habit.",
        "You're creating excellence.",
        "Champions are made like this.",
        "You're on the brink of a breakthrough!",
        "Perfect 10—unstoppable! Don't let this fall"
    )

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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Current:"
                                )
                                Text(
                                    text = "${analyticsModel.streak!!.first}    \uD83C\uDFAF"
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Longest:"
                                )
                                Text(
                                    text = "  ${analyticsModel.streak!!.second}    \uD83C\uDFAF"
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                val index = if (analyticsModel.streak!!.first>=10) 10
                                else analyticsModel.streak.first
                                ScrollingTextAnimation(
                                    text = streakQuotes[index]
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScrollingTextAnimation(text: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "animation")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "animation"
    )
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red, // Starting color
        targetValue = Color.Green, // Final color
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "colorAnimation"
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            modifier = Modifier
                .offset(x = offsetX.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = color
            ),
            maxLines = 2
        )
    }
}
