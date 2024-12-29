package tech.toshitworks.attendancechahiye.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.toshitworks.attendancechahiye.presentation.screen.settings_screen.SettingsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen.EditAttendanceScreen
import tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen.EditAttendanceScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen.EditInfoScreen
import tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen.EditScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.events_screen.EventsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.events_screen.EventsScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.export_screen.ExportScreen
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreen
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.notification_screen.NotificationScreen
import tech.toshitworks.attendancechahiye.presentation.screen.settings_screen.SettingScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreen
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenViewModel

@Composable
fun NavHomeGraph(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String,
    homeScreenViewModel: HomeScreenViewModel
) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = ScreenRoutes.TodayAttendance.route){
                val viewModel: TodayAttendanceScreenViewModel = hiltViewModel()
                TodayAttendanceScreen(
                    modifier,
                    viewModel,
                    navController
                )
            }
            composable(route = ScreenRoutes.AnalyticsScreen.route) {
                val viewModel: AnalyticsScreenViewModel = hiltViewModel()
                AnalyticsScreen(
                    modifier,
                    viewModel,
                    homeScreenViewModel
                )
            }
            composable(route = ScreenRoutes.EditInfoScreen.route){
                val viewModel: EditScreenViewModel = hiltViewModel()
                EditInfoScreen(
                    modifier,
                    viewModel,
                    homeScreenViewModel
                )
            }
            composable(route = ScreenRoutes.NotesScreen.route){
                val viewModel: NotesScreenViewModel = hiltViewModel()
                NotesScreen(
                    modifier,
                    viewModel
                )
            }
            composable(route = ScreenRoutes.EventsScreen.route){
                val viewModel: EventsScreenViewModel = hiltViewModel()
                EventsScreen(
                    modifier,
                    viewModel
                )
            }
            composable(route = ScreenRoutes.ExportScreen.route){
                ExportScreen(
                    modifier,
                    homeScreenViewModel
                )
            }
            composable(route = ScreenRoutes.SettingsScreen.route){
                val viewModel: SettingScreenViewModel = hiltViewModel()
                SettingsScreen(
                    modifier,
                    viewModel
                )
            }
            composable(route = ScreenRoutes.EditAttendanceScreen.route){
                val viewModel: EditAttendanceScreenViewModel = hiltViewModel()
                EditAttendanceScreen(
                    modifier,
                    viewModel,
                    homeScreenViewModel
                )
            }
            composable(route = ScreenRoutes.NotificationScreen.route){
                NotificationScreen(
                    modifier
                )
            }
        }
    }
