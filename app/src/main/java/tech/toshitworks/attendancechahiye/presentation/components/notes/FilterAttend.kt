package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.Filters
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenEvents

@Composable
fun FilterAttend(
    byAttendance: Pair<Boolean,Boolean>,
    onEvent: (NotesScreenEvents) -> Unit
) {
    val selected = remember {
        mutableStateOf(Pair(1,1))
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            modifier = Modifier,
            text = "By Attendance",
        )
        Text(
            text = "Present",
            textAlign = TextAlign.Center,
            color = if(selected.value.first == 1) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
        IconButton(
            onClick = {
                onEvent(NotesScreenEvents.OnChangeFilter(Filters.Attend(Pair(!byAttendance.first,byAttendance.second))))
            }
        ) {
            Icon(
                imageVector = if (byAttendance.first) Icons.Default.Check else Icons.Default.Close,
                contentDescription = "by present"
            )
        }
        Text(
            text = "Absent",
            textAlign = TextAlign.Center,
            color = if(selected.value.first == 1) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
        IconButton(
            onClick = {
                onEvent(NotesScreenEvents.OnChangeFilter(Filters.Attend(Pair(byAttendance.first,!byAttendance.second))))
            }
        ) {
            Icon(
                imageVector = if (byAttendance.second) Icons.Default.Check else Icons.Default.Close,
                contentDescription = "by absent"
            )
        }
    }
}