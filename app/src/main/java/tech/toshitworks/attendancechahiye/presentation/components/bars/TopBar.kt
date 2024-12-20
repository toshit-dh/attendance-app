package tech.toshitworks.attendancechahiye.presentation.components.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    drawerState: DrawerState,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val screen = navController.currentBackStackEntryAsState().value?.destination?.route
    val isNotificationScreen = screen == ScreenRoutes.NotificationScreen.route
    TopAppBar(
        title = {
            Text(
                text = "Attendance Chahiye",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
        ),
        navigationIcon = {
            IconButton(
                onClick ={
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Menu,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        actions = {
            if (!isNotificationScreen)
                IconButton(
                    onClick = {
                        navController.navigate(ScreenRoutes.NotificationScreen.route)
                    }
                ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "notification button",
                            tint = MaterialTheme.colorScheme.background
                        )
                }
        }
    )
}