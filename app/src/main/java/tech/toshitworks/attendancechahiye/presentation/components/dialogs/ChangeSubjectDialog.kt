package tech.toshitworks.attendancechahiye.presentation.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel

@Composable
fun ChangeSubjectDialog(
    subjects: List<SubjectModel>,
    onDismiss: () -> Unit,
    onUpdatePeriod: (String,String) -> Unit,
) {
    val subjectName = remember { mutableStateOf("") }
    val facultyName = remember { mutableStateOf("") }
    val isDropDownOpen = remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Update Period")
        },
        text = {
            Column {
                Text(text = "Enter subject name:")
                TextField(
                    modifier = Modifier
                        .clickable {
                            isDropDownOpen.value = true
                        },
                    value = subjectName.value,
                    onValueChange = {
                        subjectName.value = it
                    },
                    label = { Text("Subject Name") },
                    enabled = false
                )
                TextField(
                    value = facultyName.value,
                    onValueChange = {
                        facultyName.value = it
                    },
                    label = { Text("Faculty Name") },
                    enabled = false
                )
                DropdownMenu(
                    expanded = isDropDownOpen.value,
                    onDismissRequest = {
                        isDropDownOpen.value = false
                    }
                ) {
                    subjects.forEach{
                        DropdownMenuItem(
                            text = {
                                Text(text = it.name)
                            },
                            onClick = {
                                subjectName.value = it.name
                                facultyName.value = it.facultyName
                                isDropDownOpen.value = false
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onUpdatePeriod(subjectName.value,facultyName.value)
                },
                enabled = subjectName.value.isNotBlank() && facultyName.value.isNotBlank()
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

