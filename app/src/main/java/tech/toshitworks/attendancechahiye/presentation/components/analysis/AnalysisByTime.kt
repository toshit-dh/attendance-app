package tech.toshitworks.attendancechahiye.presentation.components.analysis


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            Column {
                when (it) {
                    0 -> {
                        Text(text = "Lectures By Day")
                        Text("j")
                    }

                    1 -> {
                        Text(text = "Lectures By Week")
                    }

                    2 -> {
                        Text(text = "Lectures By Month")
                    }
                }
            }
        }
    }


@Composable
fun AnalysisChart(

) {

}