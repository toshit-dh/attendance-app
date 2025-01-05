package tech.toshitworks.attendo.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import tech.toshitworks.attendo.domain.model.SubjectModel

@Composable
fun AddSubjectDialog(
    onDismiss: () -> Unit,
    onAddSubject: (SubjectModel) -> Unit,
) {
    val subjectName = remember { mutableStateOf("") }
    val facultyName = remember { mutableStateOf("") }
    val isAttendanceCounted = remember { mutableStateOf(false) }
    val subject = SubjectModel(name = subjectName.value, facultyName =  facultyName.value, isAttendanceCounted = isAttendanceCounted.value)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add New Subject")
        },
        text = {
            Column {
                Text(text = "Enter subject name:")
                TextField(
                    value = subject.name,
                    onValueChange = {
                        subjectName.value = it
                    },
                    label = { Text("Subject Name") }
                )
                Text(text = "Enter faculty name:")
                TextField(
                    value = subject.facultyName,
                    onValueChange = {
                        facultyName.value = it
                    },
                    label = { Text("Faculty Name") }
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Is Attendance Counted?")
                    Checkbox(
                        checked = subject.isAttendanceCounted,
                        onCheckedChange = {
                            isAttendanceCounted.value = it
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddSubject(subject)
                },
                enabled = subject.name.isNotBlank() && subject.facultyName.isNotBlank()
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
