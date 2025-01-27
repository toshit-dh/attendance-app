package tech.toshitworks.attendo.presentation.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import tech.toshitworks.attendo.domain.model.NoteModel

@Composable
fun AddNoteDialog(
    note: NoteModel?,
    onDismiss: () -> Unit,
    onAddNote: (String) -> Unit,
) {
    val content = remember {
        mutableStateOf(note?.content?:"")
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (note != null) "Edit Note" else "Add Note",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = content.value,
                    onValueChange = {
                        content.value = it
                    },
                    label = {
                        Text(
                            text = "Content"
                        )
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddNote(content.value.trimEnd())
                },
                enabled = content.value.isNotBlank() && content.value.trimEnd() != note?.content
            ) {
                Text(
                    text = if (note != null) "Edit" else "Add"
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
