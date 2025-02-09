package tech.toshitworks.attendo.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenEvents

@Composable
fun NoAttendanceSubject(
    homeScreenEvents: (HomeScreenEvents) -> Unit,
    onNavigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Attendance is not counted for this subject",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "No attendance worries! Enjoy your studies!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Explore other subjects or activities to stay engaged!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Edit the subject to include attendance",
            textAlign = TextAlign.Justify
        )
        Button(
            onClick = {
                homeScreenEvents(
                    HomeScreenEvents.OnEditTypeChange(1)
                )
                onNavigation()
            }
        ) {
            Text(
                text = "Click Here To Edit"
            )
        }
    }
}
