package tech.toshitworks.attendancechahiye.presentation.screen.events_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.AddEventDialog
import tech.toshitworks.attendancechahiye.utils.colors
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
            if (state.upcomingEvents.isEmpty())
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_lamp),
                        contentDescription = "No Upcoming Events"
                    )
                    Text(
                        text = "No Upcoming Events",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Click the + button to add an event",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ElevatedButton(
                        onClick = {
                            isAddEventDialogOpen.value = true
                        }
                    ) {
                        Text(
                            text = "Add Event",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(
                            text = "View All Events",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            else
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
                                text = "${if (state.isUpcomingEvents) "All" else "Future"} Events?",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
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
                                        append("All Events")
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
                                            Color.Yellow.copy(
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
                                                    color = colors.random()
                                                )
                                            )
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
                                            if (state.upcomingEvents.contains(it))
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
                    }
                }
        }
        if (isAddEventDialogOpen.value) {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val endDate = LocalDate.parse(state.endDate, dateFormatter)
            AddEventDialog(
                endDate = endDate,
                eventEdit = event.value,
                subjectList = state.subjectList,
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
