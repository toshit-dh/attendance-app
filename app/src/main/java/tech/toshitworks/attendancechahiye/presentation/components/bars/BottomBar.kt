package tech.toshitworks.attendancechahiye.presentation.components.bars

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tech.toshitworks.attendancechahiye.navigation.BottomScreens

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val selectedItem = remember {
        mutableStateOf(BottomScreens.TodayAttendance)
    }
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
            ),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        BottomScreens.entries.forEach {
            NavigationBarItem(
                selected = selectedItem.value == it,
                onClick = {
                    selectedItem.value = it
                    navController.navigate(it.route)
                },
                icon = {
                    Icon(
                        tint = if (it == selectedItem.value) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.background,
                        imageVector = if (it == selectedItem.value) it.iconFilled else it.iconOutlined,
                        contentDescription = it.description
                    )
                },
            )
        }
    }
}

