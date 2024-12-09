package tech.toshitworks.attendancechahiye.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.toshitworks.attendancechahiye.presentation.components.bars.BottomBar
import tech.toshitworks.attendancechahiye.presentation.components.bars.TopBar
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreen
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenViewModel

@Composable
fun NavHomeGraph(
    drawerState: DrawerState,
    navController: NavHostController,
    startDestination: String
) {
    Scaffold(
        topBar = {
            TopBar(drawerState = drawerState)
        },
        bottomBar = {
            BottomBar(navController)
        },
    ) {
        val modifier = Modifier.padding(it)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = ScreenRoutes.TodayAttendance.route){
                val viewModel: TodayAttendanceScreenViewModel = hiltViewModel()
                TodayAttendanceScreen(
                    modifier,
                    viewModel,
                )
            }
            composable(route = ScreenRoutes.AnalyticsScreen.route) {
                val viewModel: AnalyticsScreenViewModel = hiltViewModel()
                AnalyticsScreen(
                    modifier,
                    viewModel,
                )
            }
        }
    }
}