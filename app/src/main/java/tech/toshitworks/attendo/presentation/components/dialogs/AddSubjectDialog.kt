package tech.toshitworks.attendo.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.SubjectModel

@Composable
fun AddSubjectDialog(
    subjectModel: SubjectModel? = null,
    onDismiss: () -> Unit,
    onAddSubject: (SubjectModel) -> Unit,
    onEditSubject: (SubjectModel) -> Unit
) {
    val subjectName = remember {
        mutableStateOf(subjectModel?.name ?: "")
    }
    val facultyName = remember {
        mutableStateOf(subjectModel?.facultyName ?: "")
    }
    val isAttendanceCounted = remember {
        mutableStateOf(subjectModel?.isAttendanceCounted ?: false)
    }
    val subject = SubjectModel(
        name = subjectName.value,
        facultyName = facultyName.value,
        isAttendanceCounted = isAttendanceCounted.value
    )
    val text = if (subjectModel == null) "Add New Subject" else "Edit Subject"
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = subject.name,
                    onValueChange = {
                        subjectName.value = it
                    },
                    label = {
                        Text(
                            text = "Enter Subject Name",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                OutlinedTextField(
                    value = subject.facultyName,
                    onValueChange = {
                        facultyName.value = it
                    },
                    label = {
                        Text(
                            text = "Faculty Name",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Is attendance counted?",
                    )
                    Checkbox(
                        checked = subject.isAttendanceCounted,
                        onCheckedChange = {
                            isAttendanceCounted.value = it
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "info"
                    )
                    Text(
                        text = "Enter a subject as initials.\n" +
                                "Enter faculty name in two words."
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (subjectModel == null)
                        onAddSubject(subject)
                    else
                        onEditSubject(subject)
                },
                enabled = subject.name.isNotBlank() && subject.facultyName.isNotBlank()
            ) {
                Text(
                    text= if (subjectModel == null) "Add" else "Edit",
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
