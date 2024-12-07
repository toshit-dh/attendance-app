package tech.toshitworks.attendancechahiye.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.EditNote
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
}

enum class BottomScreens(
    val description: String,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector
) {
    TodayAttendance("today-attendance",Icons.Outlined.Today,Icons.Filled.Today),
    AttendanceAnalysis("attendance-analysis",Icons.Outlined.Analytics,Icons.Filled.Analytics),
    EditAttendance("edit-attendance",Icons.Outlined.EditNote,Icons.Filled.EditNote)
}
