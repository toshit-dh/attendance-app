package tech.toshitworks.attendancechahiye.presentation.screen.onboarding_screen

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingScreenViewModel,
    navHostController: NavHostController
) {
    val onEvent = viewModel::onEvent
    Text(
        text = "Hello ",
        color = MaterialTheme.colorScheme.primary
    )
    Button(onClick = {
        onEvent(OnBoardingScreenEvents.OnNextClick)
        navHostController.popBackStack()
        navHostController.navigate(ScreenRoutes.FormScreen.route)
    }) { }
}