package tech.toshitworks.attendancechahiye.presentation.components.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenStates

@Composable
fun TopAppBarActions(
    navController: NavHostController,
    screen: String?,
    state: HomeScreenStates,
    onEvent: (HomeScreenEvents) -> Unit
){
    when (screen) {
        ScreenRoutes.EditAttendanceScreen.route -> {
            IconButton(
                onClick = {
                    onEvent(HomeScreenEvents.OnEditAttendanceCalendarClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "select edit attendance date"
                )
            }
        }
        ScreenRoutes.AnalyticsScreen.route -> {
            IconButton(
                onClick = {
                    onEvent(HomeScreenEvents.OnSearchSubject)
                }
            ) {
                Icon(
                    imageVector = if (!state.isSubjectSearchOpen) Icons.Default.Search else Icons.Default.SearchOff,
                    contentDescription = "search subject for analysis"
                )
            }
        }
    }
    if (screen != ScreenRoutes.NotificationScreen.route)
        IconButton(
            onClick = {
                navController.navigate(ScreenRoutes.NotificationScreen.route)
            }
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "notification button",
                tint = MaterialTheme.colorScheme.primary
            )
        }

}