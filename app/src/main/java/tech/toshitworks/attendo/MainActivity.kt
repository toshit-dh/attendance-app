package tech.toshitworks.attendo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.toshitworks.attendo.navigation.NavGraph
import tech.toshitworks.attendo.presentation.screen.splash_screen.SplashScreenViewModel
import tech.toshitworks.attendo.ui.theme.AttendoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            AttendoTheme {
                val screen = intent.getStringExtra("screen")
                val splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
                val isLoading by splashScreenViewModel.isLoading.collectAsState()
                val screenRoute by splashScreenViewModel.screenRoute.collectAsState()
                splashScreen.setKeepOnScreenCondition { isLoading }
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    startDestination = screenRoute,
                    homeStartDestination = screen
                )
            }
        }
    }
}