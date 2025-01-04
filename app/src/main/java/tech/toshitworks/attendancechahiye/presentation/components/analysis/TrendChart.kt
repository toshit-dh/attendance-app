package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import tech.toshitworks.attendancechahiye.domain.model.AnalyticsByWeek
import java.time.LocalDate
import kotlin.reflect.KFunction2

@Composable
fun TrendAnalysis(
    analyticsByWeek: List<AnalyticsByWeek>,
    getWeek: KFunction2<Int, Int, Pair<LocalDate, LocalDate>>
) {
    val maxLecturesPresent = analyticsByWeek.maxOf {
        it.lecturesPresent
    }
    val points = analyticsByWeek.mapIndexed { idx, it ->
        Point(
            (idx).toFloat(),
            it.lecturesPresent.toFloat()
        )
    }
    val xLabel = List(points.size){ idx->
        val yearWeek = analyticsByWeek[idx].yearWeek.split('-')
        val (start, end) = getWeek(yearWeek[0].toInt(),yearWeek[1].toInt())
        start.toString().substring(5)
    }
    val xAxisData = AxisData.Builder()
        .axisStepSize(45.dp)
        .backgroundColor(Color.White)
        .steps(points.size-1)
        .labelData { i ->
            xLabel[i]
        }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(6)
        .backgroundColor(Color.White)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            (maxLecturesPresent*i/6).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = points,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )
    Card  {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Trend Analysis By Week",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Box {
                LineChart(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    lineChartData = lineChartData
                )
            }
        }
    }
}