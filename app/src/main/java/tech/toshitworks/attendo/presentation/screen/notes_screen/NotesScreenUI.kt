package tech.toshitworks.attendo.presentation.screen.notes_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.notes.FiltersRow
import tech.toshitworks.attendo.presentation.components.notes.NoteCard

@Composable
fun NotesScreen(
    modifier: Modifier,
    viewModel: NotesScreenViewModel,
    isFilterRowVisible: Boolean,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val onEvent = viewModel::onEvent
    if(!state.isLoading)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (state.notes.isEmpty())
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_books),
                        contentDescription = "no notes"
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(35.dp),
                        text = "No notes added. Add some to get started!. Go to Today's Attendance Screen to add some notes. ",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Justify
                    )
                    ElevatedButton(
                        onClick = {
                            navController.navigate(ScreenRoutes.TodayAttendance.route)
                        }
                    ) {
                        Text(
                            text = "Go to Today's Attendance Screen",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
            else if (isFilterRowVisible)
                FiltersRow(
                    modifier = Modifier
                        .weight(3f),
                    states = state,
                    onEvent = onEvent
                )
            LazyColumn(
                modifier = Modifier
                    .weight(8f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(state.filteredNotes) {
                    NoteCard(
                        state = state,
                        note = it.note,
                        attendance = it.attendance
                    )
                }
            }
        }
}

