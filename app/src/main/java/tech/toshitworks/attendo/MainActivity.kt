package tech.toshitworks.attendo

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendo.di.RepoEntryPoint
import tech.toshitworks.attendo.navigation.NavGraph
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.screen.splash_screen.SplashScreenViewModel
import tech.toshitworks.attendo.ui.theme.AttendoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            val prefs =
                EntryPointAccessors.fromApplication(applicationContext, RepoEntryPoint::class.java)
                    .dataStoreRepository()
            val themeState by prefs.readThemeState().collectAsStateWithLifecycle(0)
            AttendoTheme(
                themeState = themeState,
            ) {
                val screen = intent.getStringExtra("screen")
                val splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
                val isLoading by splashScreenViewModel.isLoading.collectAsState()
                val screenRoute by splashScreenViewModel.screenRoute.collectAsState()
                updateShortcut(screenRoute == ScreenRoutes.HomeScreen.route)
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
    private fun updateShortcut(shouldBeEnabled: Boolean) {
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        if (shouldBeEnabled) {
            val timetableShortcut = ShortcutInfo.Builder(this, "view_timetable")
                .setShortLabel("View Timetable")
                .setLongLabel("View Timetable")
                .setIcon(Icon.createWithResource(this, R.drawable.timetable))
                .setIntent(Intent(this, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("screen", ScreenRoutes.EditInfoScreen.route)
                })
                .build()
            val notesShortcut = ShortcutInfo.Builder(this, "view_notes")
                .setShortLabel("Notes")
                .setLongLabel("View Notes")
                .setIcon(Icon.createWithResource(this, R.drawable.notes))
                .setIntent(Intent(this, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("screen", ScreenRoutes.NotesScreen.route)
                })
                .build()
            val eventsShortcut = ShortcutInfo.Builder(this, "view_events")
                .setShortLabel("Events")
                .setLongLabel("View Events")
                .setIcon(Icon.createWithResource(this, R.drawable.event))
                .setIntent(Intent(this, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra("screen", ScreenRoutes.EventsScreen.route)
                })
                .build()

            shortcutManager.dynamicShortcuts = listOf(
                timetableShortcut,
                notesShortcut,
                eventsShortcut
            )
        } else {
            shortcutManager.removeAllDynamicShortcuts()
        }
    }
}
