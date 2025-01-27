package tech.toshitworks.attendo.presentation.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import tech.toshitworks.attendo.domain.model.SubjectModel

@Composable
fun ChangeSubjectDialog(
    subjects: List<SubjectModel>,
    onDismiss: () -> Unit,
    onUpdatePeriod: (SubjectModel) -> Unit,
) {
    var subject: SubjectModel? by remember {
        mutableStateOf(null)
    }
    val isDropDownOpen = remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Update Period")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Enter subject name: ",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                OutlinedTextField(
                    modifier = Modifier
                        .clickable {
                            isDropDownOpen.value = true
                        },
                    value = subject?.name?:"",
                    onValueChange = {

                    },
                    label = { Text("Subject Name") },
                    colors = TextFieldDefaults.colors().copy(
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = false
                )
                OutlinedTextField(
                    value = subject?.facultyName?:"",
                    onValueChange = {

                    },
                    colors = TextFieldDefaults.colors().copy(
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    ),
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
                                subject = it
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
                    onUpdatePeriod(subject!!)
                    onDismiss()
                },
                enabled = subject.isNotNull()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

