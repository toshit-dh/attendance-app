package tech.toshitworks.attendancechahiye.navigation

sealed class ScreenRoutes(val route: String)  {
    data object OnBoardingScreen: ScreenRoutes("onboarding_screen")
    data object HomeScreen: ScreenRoutes("home_screen")
    data object FormScreen: ScreenRoutes("form_screen")
    data object TimetableScreen: ScreenRoutes("attendance_screen")
}

enum class BottomScreens {
    TodaysAttendance,
    AttendanceAnalysis,
    EditAttendance
}
