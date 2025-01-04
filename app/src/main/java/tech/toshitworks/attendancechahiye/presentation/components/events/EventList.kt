package tech.toshitworks.attendancechahiye.presentation.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.presentation.screen.events_screen.EventScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.events_screen.EventScreenStates
import kotlin.reflect.KFunction1

@Composable
fun EventList(
    isAddEventDialogOpen: MutableState<Boolean>,
    onEvent: KFunction1<EventScreenEvents, Unit>,
    state: EventScreenStates,
    event: MutableState<EventModel?>,
    channelID: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ElevatedButton(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    isAddEventDialogOpen.value = true
                }
            ) {
                Text(
                    modifier = Modifier,
                    text = "Add Event",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    onEvent(EventScreenEvents.ToggleEvents)
                }
            ) {
                Text(
                    text = "${if (state.isUpcomingEvents) "All" else "${if (state.upcomingEvents.isEmpty()) "No " else ""}Future"} Events",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Text(
                    text = buildAnnotatedString {
                        if (state.isUpcomingEvents) {
                            append("Future Events")
                        } else {
                            append("All Events: ")
                            withStyle(style = SpanStyle(color = Color.Green.copy(alpha = 0.4f))) {
                                append("Upcoming")
                            }
                            append(" | ")
                            withStyle(style = SpanStyle(color = Color.Yellow.copy(alpha = 0.4f))) {
                                append("Active")
                            }
                            append(" | ")
                            withStyle(style = SpanStyle(color = Color.Gray.copy(alpha = 0.4f))) {
                                append("Past")
                            }
                        }
                    },
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

            }
            items(
                if (state.isUpcomingEvents)
                    state.upcomingEvents
                else
                    state.eventList
            ) {
                EventCard(state, it, event, isAddEventDialogOpen, onEvent, channelID)
            }
        }
    }
}