package tech.toshitworks.attendo.presentation.components.bars

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tech.toshitworks.attendo.navigation.BottomScreens

@Composable
fun BottomBar(
    screen: String?,
    navController: NavHostController
) {
    val isScreenABottomScreen = BottomScreens.entries.any { it.route == screen }
    BottomAppBar(
        modifier = Modifier
            .height(56.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .border(2.dp, MaterialTheme.colorScheme.primary, CutCornerShape(16.dp)),

        containerColor = MaterialTheme.colorScheme.background,
    ) {
        BottomScreens.entries.forEach {
            NavigationBarItem(
                selected = screen == it.route && isScreenABottomScreen,
                onClick = {
                    navController.navigate(it.route)
                },
                icon = {
                    Icon(
                        tint = if (!isScreenABottomScreen) MaterialTheme.colorScheme.primary else if (it.route == screen) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                        imageVector = if (it.route == screen) it.iconFilled else it.iconOutlined,
                        contentDescription = it.description
                    )
                },
            )
        }
    }
}

