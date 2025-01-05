package tech.toshitworks.attendo.presentation.components.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenStates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: HomeScreenStates,
    onEvent: (HomeScreenEvents) -> Unit,
    screen: String?,
    drawerState: DrawerState,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = {
            TopAppBarTitle(
                state = state,
                screen = screen,
                onEvent = onEvent
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Default.Menu,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            TopAppBarActions(
                navController = navController,
                screen = screen,
                state = state,
                onEvent = onEvent
            )
        }
    )
}
