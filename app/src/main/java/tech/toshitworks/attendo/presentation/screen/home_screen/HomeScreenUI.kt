package tech.toshitworks.attendo.presentation.screen.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tech.toshitworks.attendo.navigation.NavHomeGraph
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.bars.BottomBar
import tech.toshitworks.attendo.presentation.components.bars.FloatingButton
import tech.toshitworks.attendo.presentation.components.bars.NavigationDrawer
import tech.toshitworks.attendo.presentation.components.bars.TopBar

@Composable
fun HomeScreen(
    homeStartDestination: String?,
    viewModel: HomeScreenViewModel,
    beforeNavController: NavHostController,
    onLoaded: () -> Unit,
) {
    LaunchedEffect(Unit) {
        onLoaded()
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    if (homeStartDestination == ScreenRoutes.EditInfoScreen.route) onEvent(HomeScreenEvents.OnEditTypeChange(2))
    val navController = rememberNavController()
    val screen = navController.currentBackStackEntryAsState().value?.destination?.route
    val isEditAttendanceScreen = screen == ScreenRoutes.EditAttendanceScreen.route
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    NavigationDrawer(
        modifier = Modifier,
        navController = navController,
    ){drawerState->
        Scaffold(
            topBar = {
                TopBar(
                    screen = screen,
                    drawerState = drawerState,
                    navController = navController,
                    state = state,
                    onEvent = onEvent
                )
            },
            bottomBar = {
                BottomBar(
                    screen = screen,
                    navController = navController
                )
            },
            floatingActionButton = {
                if (isEditAttendanceScreen)
                    FloatingButton{
                        onEvent(HomeScreenEvents.OnEditAttendanceDateSelected(null))
                        onEvent(HomeScreenEvents.OnAddExtraAttendanceClick)
                    }
            },
            floatingActionButtonPosition = FabPosition.Center,
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
        ) {
            val modifier = Modifier.padding(it)
            NavHomeGraph(
                modifier = modifier,
                beforeNavController = beforeNavController,
                navController = navController,
                snackBarHostState = snackBarHostState,
                startDestination = homeStartDestination?:ScreenRoutes.TodayAttendance.route,
                homeScreenStates = state,
                homeScreenEvents = onEvent,
            )
        }
    }
}
