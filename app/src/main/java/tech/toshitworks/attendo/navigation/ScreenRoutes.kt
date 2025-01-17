package tech.toshitworks.attendo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenRoutes(val route: String)  {
    data object OnBoardingScreen: ScreenRoutes("onboarding_screen")
    data object HomeScreen: ScreenRoutes("home_screen")
    data object FormScreen: ScreenRoutes("form_screen")
    data object TimetableScreen: ScreenRoutes("attendance_screen")
    data object TodayAttendance: ScreenRoutes("today_attendance_screen")
    data object AnalyticsScreen: ScreenRoutes("analytics_screen")
    data object EditAttendanceScreen : ScreenRoutes("edit_attendance_screen")
    data object EditInfoScreen: ScreenRoutes("edit_info_screen")
    data object EventsScreen: ScreenRoutes("events_screen")
    data object NotesScreen : ScreenRoutes("notes_screen")
    data object ExportScreen : ScreenRoutes("export_screen")
    data object SettingsScreen : ScreenRoutes("settings_screen")
    data object DeleteScreen : ScreenRoutes("delete_screen")
    data object NotificationScreen : ScreenRoutes("notification_screen")

}
enum class DrawerScreens(
    val route: String,
    val description: String,
    val icon: ImageVector,
){
    EditInfo(ScreenRoutes.EditInfoScreen.route,"Edit Info",Icons.Filled.Edit),
    Notes(ScreenRoutes.NotesScreen.route,"Notes",Icons.Filled.Email),
    Events(ScreenRoutes.EventsScreen.route,"Events",Icons.Filled.EventRepeat),
    Export(ScreenRoutes.ExportScreen.route,"Export",Icons.Filled.FileOpen),
    Settings(ScreenRoutes.SettingsScreen.route,"Settings",Icons.Filled.Settings),
    Delete(ScreenRoutes.DeleteScreen.route,"Delete Data",Icons.Filled.DeleteSweep)
}
enum class BottomScreens(
    val route: String,
    val description: String,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector
) {
    TodayAttendance(ScreenRoutes.TodayAttendance.route,"today-attendance",Icons.Outlined.Today,Icons.Filled.Today),
    AttendanceAnalysis(ScreenRoutes.AnalyticsScreen.route,"attendance-analysis",Icons.Outlined.Analytics,Icons.Filled.Analytics),
    EditAttendance(ScreenRoutes.EditAttendanceScreen.route,"edit-attendance",Icons.Outlined.EditNote,Icons.Filled.EditNote)
}
