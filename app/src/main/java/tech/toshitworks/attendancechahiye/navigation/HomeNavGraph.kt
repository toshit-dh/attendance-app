package tech.toshitworks.attendancechahiye.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen.EditAttendanceScreen
import tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen.EditAttendanceScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen.EditInfoScreen
import tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen.EditScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.events_screen.EventsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.events_screen.EventsScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.export_screen.ExportScreen
import tech.toshitworks.attendancechahiye.presentation.screen.export_screen.ExportScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenStates
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreen
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.notification_screen.NotificationScreen
import tech.toshitworks.attendancechahiye.presentation.screen.settings_screen.SettingScreenViewModel
import tech.toshitworks.attendancechahiye.presentation.screen.settings_screen.SettingsScreen
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreen
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenViewModel

@Composable
fun NavHomeGraph(
    modifier: Modifier,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    startDestination: String,
    homeScreenStates: HomeScreenStates,
    homeScreenEvents: (HomeScreenEvents)->Unit
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
                    navController,
                    viewModel,
                    homeScreenStates.subjectList,
                    homeScreenStates.isSubjectSearchOpen,
                    homeScreenStates.analysisSubject,
                    homeScreenEvents
                )
            }
            composable(route = ScreenRoutes.EditInfoScreen.route){
                val viewModel: EditScreenViewModel = hiltViewModel()
                EditInfoScreen(
                    modifier,
                    viewModel,
                    snackBarHostState,
                    homeScreenStates.editInfo
                )
            }
            composable(route = ScreenRoutes.NotesScreen.route){
                val viewModel: NotesScreenViewModel = hiltViewModel()
                NotesScreen(
                    modifier,
                    viewModel,
                    homeScreenStates.isFilterRowVisible
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
                val viewModel: ExportScreenViewModel = hiltViewModel()
                ExportScreen(
                    modifier,
                    snackBarHostState,
                    viewModel
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
                    homeScreenStates.isAddExtraAttendanceDialogOpen,
                    homeScreenStates.isEditAttendanceDatePickerOpen,
                    homeScreenStates.editAttendanceDate,
                    homeScreenEvents
                )
            }
            composable(route = ScreenRoutes.NotificationScreen.route){
                NotificationScreen(
                    modifier
                )
            }
        }
    }
