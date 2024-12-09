package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
    println(analyticsByWeek)
    val maxLecturesPresent = analyticsByWeek.maxOf {
        it.lecturesPresent
    }
    val points = listOf(Point(0f,0f)) +  analyticsByWeek.mapIndexed { idx, it ->
        Point(
            (idx+1).toFloat(),
            it.lecturesPresent.toFloat()
        )
    }
    val endDay = analyticsByWeek.last().yearWeek.split('-')
    val (_,end) = getWeek(endDay[0].toInt(),endDay[1].toInt())
    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.White)
        .steps(points.size-1)
        .labelData { i ->
            if (i==points.size-1) return@labelData end.toString().substring(5)
            val yearWeek = analyticsByWeek[i].yearWeek.split('-')
            val (start, _) = getWeek(yearWeek[0].toInt(),yearWeek[1].toInt())
            start.toString().substring(5)
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
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Trend Analysis",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp)),
            lineChartData = lineChartData
        )
    }
}