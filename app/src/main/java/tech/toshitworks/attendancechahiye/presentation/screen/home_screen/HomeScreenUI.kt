package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tech.toshitworks.attendancechahiye.navigation.NavHomeGraph
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.components.bars.BottomBar
import tech.toshitworks.attendancechahiye.presentation.components.bars.FloatingButton
import tech.toshitworks.attendancechahiye.presentation.components.bars.NavigationDrawer
import tech.toshitworks.attendancechahiye.presentation.components.bars.TopBar

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val navController = rememberNavController()
    val screen = navController.currentBackStackEntryAsState().value?.destination?.route
    val isEditAttendanceScreen = screen == ScreenRoutes.EditAttendanceScreen.route
    NavigationDrawer(
        modifier = Modifier,
        navController = navController,
    ){drawerState->
        Scaffold(
            topBar = {
                TopBar(
                    screen = screen,
                    drawerState = drawerState,
                    navController = navController
                )
            },
            bottomBar = {
                BottomBar(
                    screen = screen,
                    navController = navController)
            },
            floatingActionButton = {
                if (isEditAttendanceScreen)
                    FloatingButton{
                        viewModel.floatingButtonClick(true)
                    }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            val modifier = Modifier.padding(it)
            NavHomeGraph(
                modifier = modifier,
                homeScreenViewModel = viewModel,
                navController = navController,
                startDestination = ScreenRoutes.TodayAttendance.route
            )
        }
    }
}
