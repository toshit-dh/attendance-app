package tech.toshitworks.attendancechahiye.presentation.screen.events_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.AddEventDialog
import tech.toshitworks.attendancechahiye.presentation.components.events.EventList
import tech.toshitworks.attendancechahiye.presentation.components.events.NoUpcomingEvents
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EventsScreen(
    modifier: Modifier,
    viewModel: EventsScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val isAddEventDialogOpen = remember {
        mutableStateOf(false)
    }
    val viewPast = remember {
        mutableStateOf(false)
    }
    val event = remember {
        mutableStateOf<EventModel?>(null)
    }
    val channelID = stringResource(R.string.channel_id)
    if (!state.isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewPast.value)
                EventList(isAddEventDialogOpen, onEvent, state, event, channelID)
            if (state.upcomingEvents.isEmpty() && state.activeEvents.isEmpty())
                NoUpcomingEvents(
                    onDialogEvent = {
                        isAddEventDialogOpen.value = !isAddEventDialogOpen.value
                    }
                ){
                    viewPast.value = true
                }
            else
                EventList(isAddEventDialogOpen, onEvent, state, event, channelID)
        }
        if (isAddEventDialogOpen.value) {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val endDate = LocalDate.parse(state.endDate, dateFormatter)
            AddEventDialog(
                endDate = endDate,
                eventEdit = event.value,
                subjectList = state.subjectList.filter {
                    it.name != "Lunch" && it.name != "No Period"
                },
                onDismiss = {
                    isAddEventDialogOpen.value = false
                }
            ) { sm, date, content, id, local ->
                onEvent(
                    EventScreenEvents.OnAddEvent(
                        EventModel(
                            id = id,
                            subjectModel = sm,
                            date = date,
                            dateLocal = local,
                            content = content
                        ),
                        channelID,
                        false
                    )
                )
                isAddEventDialogOpen.value = false
            }
        }
    }
}