package tech.toshitworks.attendo.presentation.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.model.TimetableModel

@Composable
fun SelectSubjectDialog(
    subjectList: List<SubjectModel>,
    periodModel: PeriodModel,
    dayModel: DayModel,
    onDismiss: () -> Unit,
    onAddSubject: (TimetableModel) -> Unit,
) {
    val isSubjectDropDownOpen = remember {
        mutableStateOf(false)
    }
    val subject: MutableState<SubjectModel?> = remember {
        mutableStateOf(null)
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(
                        style = ParagraphStyle(
                            textAlign = TextAlign.Center
                        )
                    ) {
                        append("Add Period Details")
                    }
                    withStyle(
                        style = ParagraphStyle(
                            textAlign = TextAlign.Center
                        )
                    )
                    {
                        append("${dayModel.name} : ${periodModel.startTime}")
                    }
                }
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = subject.value?.name ?: "",
                    onValueChange = {},
                    label = {
                        Text("Add A Subject")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isSubjectDropDownOpen.value = true },
                    colors = TextFieldDefaults.colors().copy(
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = false,
                    readOnly = true
                )
                DropdownMenu(
                    expanded = isSubjectDropDownOpen.value,
                    onDismissRequest = {
                        isSubjectDropDownOpen.value = false
                    }
                ) {
                    subjectList.forEach { subjectM ->
                        DropdownMenuItem(
                            text = {
                                Text(subjectM.name)
                            },
                            onClick = {
                                subject.value = subjectM
                                isSubjectDropDownOpen.value = false
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddSubject(
                        TimetableModel(
                            subject = subject.value!!,
                            period = periodModel,
                            day = dayModel
                        )
                    )
                    onDismiss()
                },
                enabled = subject.value != null
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

