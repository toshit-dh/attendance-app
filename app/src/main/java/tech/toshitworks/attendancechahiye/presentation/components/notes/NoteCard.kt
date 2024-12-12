package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.Six
import tech.toshitworks.attendancechahiye.presentation.components.analysis.colors
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenStates

@Composable
fun NoteCard(
    state: NotesScreenStates,
    note: NoteModel,
    attendance: Six
) {
    val day = state.days.find { it.id == attendance.dayId }!!
    val period = state.periods.find { it.id == attendance.periodId }!!
    val subject = state.subjects.find { it.id == attendance.subjectId }!!
    Card {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = subject.name,
                    color = colors.random(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = "Day: ${day.name}",
                    color = colors[day.id!!.toInt()],
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = "Period: ${period.id}",
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = attendance.date,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier
                    .weight(1f)
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    text = if (attendance.isPresent) "Present" else "Absent",
                    color = if (attendance.isPresent) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Content: ${note.content}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
