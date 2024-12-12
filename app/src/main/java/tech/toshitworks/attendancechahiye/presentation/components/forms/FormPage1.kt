package tech.toshitworks.attendancechahiye.presentation.components.forms

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.SemesterModel
import tech.toshitworks.attendancechahiye.presentation.screen.form_screen.FormScreen1Events
import tech.toshitworks.attendancechahiye.presentation.screen.form_screen.FormScreenEvents
import java.util.Calendar

@Composable
fun FormPage1(
    state: SemesterModel?,
    onEvent: (event: FormScreen1Events) -> Unit
) {
    val context = LocalContext.current
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showMidTermDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val formattedDate = "$dayOfMonth/${month + 1}/$year"
        when {
            showStartDatePicker -> {
                onEvent(FormScreen1Events.OnStartDateChange(formattedDate))
                showStartDatePicker = false
            }
            showMidTermDatePicker -> {
                onEvent(FormScreen1Events.OnMidTermDateChange(formattedDate))
                showMidTermDatePicker = false
            }
            showEndDatePicker -> {
                onEvent(FormScreen1Events.OnEndDateChange(formattedDate))
                showEndDatePicker = false
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Add Semester Info",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text("Semester Number", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            label = { Text("Add Semester Number") },
            value = state?.semNumber?.toString() ?: "",
            onValueChange = {
                val newValue = it.toIntOrNull()
                if (newValue != null) {
                    onEvent(FormScreen1Events.OnSemesterNumberChange(newValue))
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Start Date", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state?.startDate ?: "",
            onValueChange = {},
            label = { Text("Select Start Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showStartDatePicker = true
                    DatePickerDialog(
                        context,
                        datePickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            enabled = false,

        )
        Spacer(modifier = Modifier.height(16.dp))


        Text("Mid Term Date", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state?.midTermDate ?: "",
            onValueChange = {},
            label = { Text("Select Mid Term Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showMidTermDatePicker = true
                    DatePickerDialog(
                        context,
                        datePickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            enabled = false,

        )
        Spacer(modifier = Modifier.height(16.dp))


        Text("End Date", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = state?.endDate ?: "",
            onValueChange = {},
            label = { Text("Select End Date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showEndDatePicker = true
                    DatePickerDialog(
                        context,
                        datePickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            enabled = false,

        )
    }
}
