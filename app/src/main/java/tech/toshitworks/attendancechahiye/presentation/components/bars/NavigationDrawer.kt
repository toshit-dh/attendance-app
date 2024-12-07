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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FileOpen
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.navigation.NavGraph

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    val items = listOf(
        Pair(
            first = "Edit  Info",
            second = Icons.Default.Edit
        ),
        Pair(
            first = "Notes",
            second = Icons.Default.Email
        ),
        Pair(
            first = "Export",
            second = Icons.Default.FileOpen
        )
    )
    val itemSelected = remember {
        mutableStateOf(items[0])
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val config = LocalConfiguration.current
    val modalWidth = (config.screenWidthDp * 0.7f)
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
                        items(items){
                            DrawerItem(it, itemSelected)
                        }
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        NavGraph(
            drawerState = drawerState,
            navController = navController,
            startDestination = startDestination
        )
    }
}

@Composable
private fun DrawerItem(
    it: Pair<String, ImageVector>,
    itemSelected: MutableState<Pair<String, ImageVector>>
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
                text = it.first,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        selected = itemSelected.equals(it),
        onClick = {

        },
        badge = {
            Icon(
                imageVector = it.second,
                contentDescription = it.first,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}