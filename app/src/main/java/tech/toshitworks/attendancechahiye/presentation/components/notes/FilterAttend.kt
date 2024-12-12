package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenEvents

@Composable
fun FilterAttend(
    onEvent: (NotesScreenEvents) -> Unit
) {
    val selected = remember {
        mutableStateOf(Pair(1,1))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            modifier = Modifier,
            text = "Attend",
        )
        Text(
            text = "Present",
            textAlign = TextAlign.Center,
            color = if(selected.value.first == 1) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
        Text(
            text = "Absent",
            textAlign = TextAlign.Center,
            color = if(selected.value.first == 1) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}