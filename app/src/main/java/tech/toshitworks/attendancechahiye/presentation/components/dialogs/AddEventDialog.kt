package tech.toshitworks.attendancechahiye.presentation.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import tech.toshitworks.attendancechahiye.domain.model.EventModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import java.time.LocalDate
import java.util.Locale


@Composable
fun AddEventDialog(
    endDate: LocalDate?,
    eventEdit: EventModel?,
    subjectList: List<SubjectModel>,
    onDismiss: () -> Unit,
    onConfirm: (SubjectModel, String, String,Long,LocalDate) -> Unit
) {
    val isSubjectDropDownOpen = remember {
        mutableStateOf(false)
    }
    val subject = remember {
        mutableStateOf(eventEdit?.subjectModel)
    }
    val date = remember {
        mutableStateOf(eventEdit?.date)
    }
    val dateLocal = remember {
        mutableStateOf(eventEdit?.dateLocal)
    }
    val content = remember {
        mutableStateOf(eventEdit?.content)
    }

    val maxChars = 50
    val minChars = 3

    val wordCount = content.value?.length?:0
    val isContentError = wordCount > maxChars || wordCount < minChars

    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = "Add Event")
        },
        text = {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isSubjectDropDownOpen.value = !isSubjectDropDownOpen.value
                        },
                    text = "${if (subject.value!=null) subject.value!!.name else "Select"} Subject",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                DropdownMenu(
                    expanded = isSubjectDropDownOpen.value,
                    onDismissRequest = {
                        isSubjectDropDownOpen.value = false
                    }
                ) {
                    subjectList.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    it.name
                                )
                            },
                            onClick = {
                                subject.value = it
                                isSubjectDropDownOpen.value = false
                            }
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Date: ${date.value}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                WheelDatePicker(
                    minDate = LocalDate.now(),
                    maxDate = endDate?:LocalDate.MAX,
                    startDate = if (eventEdit != null) LocalDate.parse(eventEdit.date) else LocalDate.now().plusDays(1),
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    )
                ) {
                    val formattedDate = String.format(
                        Locale.US,
                        "%04d-%02d-%02d",
                        it.year,
                        it.monthValue,
                        it.dayOfMonth
                    )
                    dateLocal.value = it
                    date.value = formattedDate
                }
                TextField(
                    value = content.value?:"",
                    onValueChange = {
                        content.value = it
                    },
                    placeholder = {
                        Text(
                            text = "Write Content Here",

                            )
                    },
                    label = {
                        Text(
                            text = "Description"
                        )
                    },
                    isError = isContentError,
                    supportingText = {
                        if (isContentError) {
                            Text(
                                text = if (wordCount > minChars) {
                                    "Content exceeds $minChars words."
                                } else {
                                    "Content must have at least $minChars words."
                                }
                            )
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val id = eventEdit?.id?:0
                    onConfirm(subject.value!!, date.value!!, content.value!!,id,dateLocal.value!!)
                },
                enabled = !isContentError && subject.value != null && date.value != null && dateLocal.value != null
            ) {
                Text(
                    if (eventEdit!=null) "Edit" else "Add"
                )
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
