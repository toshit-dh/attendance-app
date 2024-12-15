package tech.toshitworks.attendancechahiye.presentation.screen.notes_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.presentation.components.notes.FiltersRow
import tech.toshitworks.attendancechahiye.presentation.components.notes.NoteCard

@Composable
fun NotesScreen(
    modifier: Modifier,
    viewModel: NotesScreenViewModel
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
            FiltersRow(
                states = state,
                onEvent = onEvent
            )
            LazyColumn(
                modifier = Modifier,
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

