package tech.toshitworks.attendancechahiye.presentation.components.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.navigation.DrawerScreens
import tech.toshitworks.attendancechahiye.navigation.NavHomeGraph

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val config = LocalConfiguration.current
    val modalWidth = (config.screenWidthDp * 0.7f)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        modifier = modifier,
        scrimColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(modalWidth.dp),
                drawerShape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 40.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 40.dp
                ),
                drawerState = drawerState,
                drawerContainerColor = MaterialTheme.colorScheme.background,
                drawerContentColor = MaterialTheme.colorScheme.primary
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        painter = painterResource(
                            R.drawable.bg
                        ),
                        contentDescription = "background"
                    )
                    Text(
                        text = "Attendance Chahiye",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        items(DrawerScreens.entries){
                            DrawerItem(
                                it,
                                onClick = {
                                    navController.navigate(it.route)
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        NavHomeGraph(
            drawerState = drawerState,
            navController = navController,
            startDestination = startDestination
        )
    }
}

@Composable
private fun DrawerItem(
    item: DrawerScreens,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    10.dp
                )
            ),
        label = {
            Text(
                text = item.description,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        selected = false,
        onClick = onClick,
        badge = {
            Icon(
                imageVector = item.icon,
                contentDescription = item.route,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}