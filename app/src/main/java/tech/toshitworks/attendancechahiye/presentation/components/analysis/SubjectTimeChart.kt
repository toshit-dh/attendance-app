package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsModel

@Composable
fun SubjectTimeChart(
    page: Int,
   analyticsModel: AnalyticsModel,
   subjectAnalyticsModel: List<AnalyticsModel>
){
val colors = listOf(
    Color(0xFFE57373),
    Color(0xFF81C784),
    Color(0xFF64B5F6),
    Color(0xFFFFD54F),
    Color(0xFFBA68C8),
    Color(0xFFFF7043),
    Color(0xFF4CAF50),
    Color(0xFF9575CD),
    Color(0xFFFFF176),
    Color(0xFF29B6F6)
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
        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface
    )
    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie,
    )
    Column(
        modifier = Modifier.height(200.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                if (page == 0)
                    PieChart(
                        modifier = Modifier
                            .width(200.dp)
                            .height(175.dp),
                        pieChartData,
                        pieChartConfig
                    )
            }
        }
    }
}