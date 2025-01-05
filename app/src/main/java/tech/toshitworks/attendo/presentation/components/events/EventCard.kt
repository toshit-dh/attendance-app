package tech.toshitworks.attendo.presentation.components.events

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.EventModel
import tech.toshitworks.attendo.presentation.screen.events_screen.EventScreenEvents
import tech.toshitworks.attendo.presentation.screen.events_screen.EventScreenStates
import tech.toshitworks.attendo.utils.colors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.KFunction1

@Composable
fun EventCard(
    state: EventScreenStates,
    it: EventModel,
    event: MutableState<EventModel?>,
    isAddEventDialogOpen: MutableState<Boolean>,
    onEvent: KFunction1<EventScreenEvents, Unit>,
    channelID: String
) {
    val color = if (isSystemInDarkTheme()) Color.Cyan else Color.Blue
    Card(
        colors = if (state.isUpcomingEvents)
            CardDefaults.cardColors()
        else
            CardDefaults.cardColors().copy(
                containerColor = if (state.upcomingEvents.contains(it))
                    Color.Green.copy(
                        alpha = 0.2f
                    )
                else if (state.activeEvents.contains(it))
                    color.copy(
                        alpha = 0.2f
                    )
                else
                    Color.Gray.copy(
                        alpha = 0.2f
                    )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = it.subjectModel.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors().random()
                        )
                    )
                    if (state.upcomingEvents.contains(it)) {
                        IconButton(
                            onClick = {
                                event.value = it
                                isAddEventDialogOpen.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "edit"
                            )
                        }
                        IconButton(
                            onClick = {
                                onEvent(EventScreenEvents.OnDeleteEvent(it))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete"
                            )
                        }
                        IconButton(
                            onClick = {
                                if (it.notificationWorkId != null)
                                    onEvent(
                                        EventScreenEvents.OnAddEvent(
                                            it,
                                            cancel = true
                                        )
                                    )
                                else {
                                    val formatter =
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    onEvent(
                                        EventScreenEvents.OnAddEvent(
                                            it.copy(
                                                dateLocal = LocalDate.parse(
                                                    it.date,
                                                    formatter
                                                )
                                            ),
                                            channelID,
                                            false
                                        )
                                    )
                                }

                            }
                        ) {
                            Icon(
                                imageVector = if (it.notificationWorkId != null) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                                contentDescription = "notification"
                            )
                        }
                    }
                }
                Text(
                    text = it.date,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = it.content,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}