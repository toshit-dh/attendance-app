package tech.toshitworks.attendo.presentation.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun AddNoteDialog(
    contentFirst: MutableState<String>,
    onDismiss: () -> Unit,
    onAddNote: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Note")
        },
        text = {
            Column {
                TextField(
                    value = contentFirst.value,
                    onValueChange = {
                        contentFirst.value = it
                    },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddNote(contentFirst.value)
                },
                enabled = contentFirst.value.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
