package tech.toshitworks.attendo.presentation.screen.notification_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator

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
                LazyColumn {
                    items(state.first){
                        Card {
                            Column {
                                Row {
                                    Text(
                                        text = it.title,
                                    )
                                    Text(
                                        text = it.subText
                                    )
                                }
                                Text(
                                    text = it.message
                                )
                                Text(
                                    text = it.timestamp.toString()
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