package tech.toshitworks.attendancechahiye.presentation.home_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.components.bars.NavigationDrawer

@Composable
fun HomeScreen(
) {
    val navController = rememberNavController()
        NavigationDrawer(
            modifier = Modifier,
            navController = navController,
            startDestination = ScreenRoutes.TodayAttendance.route
        )
}
