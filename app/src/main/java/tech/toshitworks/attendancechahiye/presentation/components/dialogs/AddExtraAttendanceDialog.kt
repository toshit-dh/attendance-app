package tech.toshitworks.attendancechahiye.presentation.components.dialogs

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import java.util.Calendar
import java.util.Locale


@Composable
fun AddExtraAttendanceDialog(
    startDate: Long,
    endDate: Long,
    subjectList: List<SubjectModel>,
    onDismiss: () -> Unit,
    onAddAttendance: (SubjectModel,String,Boolean) -> Unit,
) {
    val subject: MutableState<SubjectModel?> = remember {
        mutableStateOf(null)
    }
    val date: MutableState<String?> = remember {
        mutableStateOf(null)
    }
    val isSubjectDropDownOpen = remember {
        mutableStateOf(false)
    }
    val isPresent: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add Extra Attendance")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Select Subject:")
                TextField(
                    modifier = Modifier
                        .clickable {
                            isSubjectDropDownOpen.value = true
                        },
                    value = subject.value?.name?:"",
                    onValueChange = {

                    },
                    label = {
                        Text(
                            "Add Subject"
                        )
                    },
                    enabled = false
                )
                DropdownMenu(
                    expanded = isSubjectDropDownOpen.value,
                    onDismissRequest = {
                        isSubjectDropDownOpen.value = false
                    }
                ){
                    subjectList.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(it.name)
                            },
                            onClick = {
                                subject.value = it
                                isSubjectDropDownOpen.value = false
                            }
                        )
                    }
                }
                TextField(
                    modifier = Modifier
                        .clickable {
                            val datePicker = DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, day: Int ->
                                    val formattedDate = String.format(
                                        Locale.US,
                                        "%04d-%02d-%02d",
                                        year,
                                        month + 1,
                                        day
                                    )
                                    date.value = formattedDate
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            val today = calendar.timeInMillis
                            val maxDate = if (today > endDate) endDate else today
                            datePicker.datePicker.minDate = startDate
                            datePicker.datePicker.maxDate = maxDate
                            datePicker.show()
                        },
                    value = date.value?:"",
                    onValueChange = {

                    },
                    label = {
                        Text(
                            "Add Date"
                        )
                    },
                    enabled = false
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "IsPresent?"
                    )
                    Checkbox(
                        checked = isPresent.value,
                        onCheckedChange = {
                            isPresent.value = it
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddAttendance(subject.value!!,date.value!!,isPresent.value)
                },
                enabled = subject.value != null && date.value != null
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
