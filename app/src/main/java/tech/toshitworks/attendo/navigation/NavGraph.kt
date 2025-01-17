package tech.toshitworks.attendo.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreen
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreen
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenViewModel
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenViewModel
import tech.toshitworks.attendo.presentation.screen.onboarding_screen.OnBoardingScreen
import tech.toshitworks.attendo.presentation.screen.onboarding_screen.OnBoardingScreenViewModel
import tech.toshitworks.attendo.presentation.screen.timetable_screen.TimeTableViewModel
import tech.toshitworks.attendo.presentation.screen.timetable_screen.TimetableScreen


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    homeStartDestination: String?,
    onLoad: () -> Unit
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = ScreenRoutes.OnBoardingScreen.route){
            val viewModel: OnBoardingScreenViewModel = hiltViewModel()
            OnBoardingScreen(
                viewModel,
                navController,
                onLoad
            )
        }
        composable(route = ScreenRoutes.FormScreen.route){
            val viewModel: FormScreenViewModel = hiltViewModel()
            FormScreen(
                viewModel,
                navController,
                onLoad)
        }
        composable(route = ScreenRoutes.TimetableScreen.route){
            val viewModel: TimeTableViewModel = hiltViewModel()
            TimetableScreen(viewModel,
                navController,
                onLoad
            )
        }
        composable(route = ScreenRoutes.HomeScreen.route){
            val viewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreen(
                homeStartDestination,
                viewModel,
                navController,
                onLoad
            )
        }

    }
}