package tech.toshitworks.attendo.presentation.screen.notification_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator
import tech.toshitworks.attendo.utils.colors
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@Composable
fun NotificationScreen(
    modifier: Modifier,
    viewModel: NotificationScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.second)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (state.first.isNotEmpty())
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ){
                    items(state.first){
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val color = colors().random()
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ){
                                        Text(
                                            text = it.title,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = color
                                        )
                                        Icon(
                                            painter = when (it.title) {
                                                "Attendance Marked" -> painterResource(R.drawable.percent)
                                                "Event Reminder" -> painterResource(R.drawable.event)
                                                else -> painterResource(R.drawable.img_notif)
                                            },
                                            contentDescription = "",
                                            tint = color
                                        )
                                    }
                                    Text(
                                        text = it.subText,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = color
                                    )
                                }
                                Text(
                                    text = it.message,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                val date = Date(it.timestamp)
                                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                                val formattedDate = formatter.format(date)
                                Text(
                                    text = "Date and Time: $formattedDate",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp
                                    )
                                )
                            }
                        }
                    }
                }
            else
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .offset(x = -(20.dp)),
                        painter = painterResource(R.drawable.img_notif),
                        contentDescription = "notification"
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )
                    Text(
                        text = "No notifications found",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
        }
    else
        LoadingIndicator(modifier)
}