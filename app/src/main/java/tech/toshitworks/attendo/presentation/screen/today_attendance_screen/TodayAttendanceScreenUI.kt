package tech.toshitworks.attendo.presentation.screen.today_attendance_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator
import tech.toshitworks.attendo.presentation.components.today_attendance.TimetableForDay
import java.time.LocalDate


@Composable
fun TodayAttendanceScreen(
    modifier: Modifier = Modifier,
    viewModel: TodayAttendanceScreenViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    var endDateNull = false
    if (!state.isLoading) {
        val startDate = state.startDate.split("-")
        val todayDate = state.date.split("-")
        val endDate = state.endDate?.split("-")
        val sdLocale = LocalDate.of(startDate[0].toInt(), startDate[1].toInt(), startDate[2].toInt())
        val tdLocale = LocalDate.of(todayDate[0].toInt(), todayDate[1].toInt(), todayDate[2].toInt())
        val edLocale = try{
            LocalDate.of(endDate?.get(0)?.toInt() ?: 0, endDate?.get(1)?.toInt() ?: 0, endDate?.get(2)?.toInt() ?: 0)
        }catch (e: Exception){
            endDateNull = true
            LocalDate.of(2000,1,1)
        }
        Column(
            modifier = modifier
        ) {
            val isValidDay = state.day != null && state.dayList.contains(state.day)
            val isInDateRange = tdLocale.isAfter(sdLocale) ||
                    (tdLocale.isEqual(sdLocale) && (endDateNull || tdLocale.isBefore(edLocale)))
            if (isValidDay && isInDateRange) {
                TimetableForDay(
                    state = state,
                    onEvent = onEvent,
                    date = state.date,
                    day = state.day!!
                ){
                    navController.navigate(ScreenRoutes.AnalyticsScreen.route + "/$it")
                }
            }else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_study),
                        contentDescription = "studying"
                    )
                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(35.dp),
                        text = "Working days drain your energy; holidays recharge it—use that energy to study and stay ahead",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Justify
                    )
                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )
                    ElevatedButton(
                        onClick = {
                            navController.navigate(ScreenRoutes.EventsScreen.route)
                        }
                    ) {
                        Text(
                            text = "View Upcoming Events",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
    else
        LoadingIndicator(modifier)
}


