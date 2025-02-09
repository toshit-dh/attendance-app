package tech.toshitworks.attendo.presentation.components.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import tech.toshitworks.attendo.navigation.RoutesArgs
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenStates

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
        ScreenRoutes.AnalyticsScreen.route + RoutesArgs.SubjectIdArg.arg -> {
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
        ScreenRoutes.NotesScreen.route -> {
            IconButton(
                onClick = {
                    onEvent(HomeScreenEvents.OnNoteFilterRowVisibilityChange)
                }
            ) {
                Icon(
                    imageVector = if (state.isFilterRowVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "filter visibility"
                )
            }
        }
        ScreenRoutes.EditInfoScreen.route -> {
            IconButton(
                onClick = {
                    onEvent(HomeScreenEvents.OnNoteFilterRowVisibilityChange)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit info"
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