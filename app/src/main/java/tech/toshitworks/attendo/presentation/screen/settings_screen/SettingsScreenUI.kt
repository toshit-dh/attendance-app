package tech.toshitworks.attendo.presentation.screen.settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.commandiron.wheel_picker_compose.WheelTimePicker
import java.time.LocalTime

@Composable
fun SettingsScreen(
    modifier: Modifier,
    viewModel: SettingScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    if (!state.isLoading) {
        val time = remember {
            mutableLongStateOf(state.notificationTime)
        }
        LazyColumn(
            modifier = modifier.padding(8.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                text = "Notifications",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            ElevatedButton(
                                onClick = {

                                }
                            ) {
                                Text(
                                    text = "Reset",
                                )
                            }
                            ElevatedButton(
                                onClick = {
                                    onEvent(SettingsScreenEvents.OnNotificationTimeChange(time.longValue))
                                }
                            ) {
                                Text(
                                    text = "Save",
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(2f),
                                text = "Select time of day for event reminder",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            WheelTimePicker(
                                modifier = Modifier
                                    .weight(1f),
                                startTime = LocalTime.ofSecondOfDay(time.longValue)
                            ) {
                                time.longValue = it.toSecondOfDay().toLong()
                            }
                        }
                    }
                }
            }
        }
    }
}