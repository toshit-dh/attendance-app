package tech.toshitworks.attendancechahiye.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

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
                text = "Add subject to a period \n" +
                        periodModel.startTime +
                        "\n ${dayModel.name}"
            )
        },
        text = {
            Column {
                TextField(
                    value = subject.value?.name ?: "",
                    onValueChange = {},
                    label = {
                        Text("Add A Subject")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isSubjectDropDownOpen.value = true }
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
                    enabled = false
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

