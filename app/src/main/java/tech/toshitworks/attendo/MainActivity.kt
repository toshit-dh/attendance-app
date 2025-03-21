package tech.toshitworks.attendo

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendo.di.RepoEntryPoint
import tech.toshitworks.attendo.navigation.NavGraph
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.ui.theme.AttendoTheme
import tech.toshitworks.attendo.utils.updateShortcut

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var setKeepOnCondition = true
        installSplashScreen().apply {
            setOnExitAnimationListener { viewProvider ->
                ObjectAnimator.ofFloat(
                    viewProvider.view, "scaleX", 0.5f, 0f
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = 300
                    doOnEnd { viewProvider.remove() }
                    start()
                }
                ObjectAnimator.ofFloat(
                    viewProvider.view, "scaleY", 0.5f, 0f
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = 300
                    doOnEnd { viewProvider.remove() }
                    start()
                }
            }
            setKeepOnScreenCondition {
                setKeepOnCondition
            }
        }
        setContent {
            val prefs =
                EntryPointAccessors.fromApplication(applicationContext, RepoEntryPoint::class.java)
                    .dataStoreRepository()
            val themeState by prefs.readThemeState().collectAsStateWithLifecycle(0)
            AttendoTheme(
                themeState = themeState,
            ) {
                val loading = remember { mutableStateOf(true) }
                val screenRoute = remember {
                    mutableStateOf(ScreenRoutes.OnBoardingScreen.route)
                }
                LaunchedEffect(Unit) {
                    val screenSelection = prefs.readScreenSelection()
                    screenRoute.value = when (screenSelection) {
                        0 -> ScreenRoutes.OnBoardingScreen.route
                        1 -> ScreenRoutes.FormScreen.route
                        2 -> ScreenRoutes.TimetableScreen.route
                        3 -> ScreenRoutes.HomeScreen.route
                        else -> ScreenRoutes.OnBoardingScreen.route
                    }
                    loading.value = false
                }
                val screen = intent.getStringExtra("screen")
                updateShortcut(this, screenRoute.value == ScreenRoutes.HomeScreen.route)
                val navController = rememberNavController()
                if (!loading.value) NavGraph(
                    navController = navController,
                    startDestination = screenRoute.value,
                    homeStartDestination = screen
                ) {
                    setKeepOnCondition = false
                }
            }
        }
    }
}
