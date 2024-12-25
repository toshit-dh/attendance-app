package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import tech.toshitworks.attendancechahiye.navigation.NavHomeGraph
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.components.bars.BottomBar
import tech.toshitworks.attendancechahiye.presentation.components.bars.FloatingButton
import tech.toshitworks.attendancechahiye.presentation.components.bars.NavigationDrawer
import tech.toshitworks.attendancechahiye.presentation.components.bars.TopBar
import tech.toshitworks.attendancechahiye.utils.SnackBarWorkerEvent

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val navController = rememberNavController()
    val screen = navController.currentBackStackEntryAsState().value?.destination?.route
    val isEditAttendanceScreen = screen == ScreenRoutes.EditAttendanceScreen.route
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(true) {
        viewModel.event.collect {
            when(it){
                is SnackBarWorkerEvent.ShowSnackBarForCSVWorker -> {
                    snackBarHostState.showSnackbar(
                        it.message
                    )
                    delay(500)
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            }
        }
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
                    navController = navController)
            },
            floatingActionButton = {
                if (isEditAttendanceScreen)
                    FloatingButton{
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
                homeScreenViewModel = viewModel,
                navController = navController,
                startDestination = ScreenRoutes.TodayAttendance.route
            )
        }
    }
}
