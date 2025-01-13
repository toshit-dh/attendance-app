package tech.toshitworks.attendo.presentation.components.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun ThemeSettings(
    themeState: Int,
    onClick: (Int) -> Unit
) {
    val themeStates = listOf("System Default", "Light Theme", "Dark Theme", "Dynamic Theme")
    val isInfoOpen = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Select Theme",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = themeStates[0],
                    fontWeight = FontWeight.Bold
                )
                Checkbox(
                    checked = themeState == 0,
                    onCheckedChange = { _ ->
                        onClick(0)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = themeStates[1],
                    fontWeight = FontWeight.Bold
                )
                Checkbox(
                    checked = themeState == 1,
                    onCheckedChange = { _ ->
                        onClick(1)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = themeStates[2],
                    fontWeight = FontWeight.Bold
                )
                Checkbox(
                    checked = themeState == 2,
                    onCheckedChange = { _ ->
                        onClick(2)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = themeStates[3],
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {
                            isInfoOpen.value = !isInfoOpen.value
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "info"
                        )
                    }
                }
                Checkbox(
                    checked = themeState == 3,
                    onCheckedChange = { _ ->
                        onClick(3)
                    }
                )
            }
            if (isInfoOpen.value)
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Dynamic Theme based on your device wallpaper or theme"
                )
        }
    }
}

