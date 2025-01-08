package tech.toshitworks.attendo.presentation.screen.settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator
import tech.toshitworks.attendo.presentation.components.settings.EventSettings
import tech.toshitworks.attendo.presentation.components.settings.MarkAttendanceSettings

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
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                EventSettings(
                    time = time.longValue,
                    onClick = {
                        if (it == "Reset")
                            time.longValue = 0
                        onEvent(SettingsScreenEvents.OnNotificationTimeChange(time.longValue))
                    }
                ){
                    time.longValue = it.toSecondOfDay().toLong()
                }
            }
            item {
                MarkAttendanceSettings(
                    doMarkAttendance = state.doMarkAttendance,
                ){
                    onEvent(SettingsScreenEvents.OnDoMarkAttendanceChange(it))
                }
            }
        }
    }
    else
        LoadingIndicator(modifier)
}