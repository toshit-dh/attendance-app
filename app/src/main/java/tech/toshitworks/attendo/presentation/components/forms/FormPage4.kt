package tech.toshitworks.attendo.presentation.components.forms

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenEvents
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenStates
import java.util.Calendar
import java.util.Locale

@Composable
fun FormPage4(
    state: FormScreenStates,
    onEvent: (event: FormScreenEvents) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val periodAdded = remember { mutableStateOf(false) }
    val startTime = remember { mutableStateOf("") }
    val endTime = remember { mutableStateOf("") }
    val breakDuration = remember { mutableStateOf("Select Period Duration") }
    val breakDurationOptions = listOf("5", "10", "15", "20", "30", "45", "60")
    val expanded = remember { mutableStateOf(false) }
    val startTimePickerDialog = remember { mutableStateOf(false) }
    val endTimePickerDialog = remember { mutableStateOf(false) }
    val timePickerListener = { hourOfDay: Int, minute: Int ->
        val formattedTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minute)
        when {
            startTimePickerDialog.value -> {
                startTime.value = formattedTime
                startTimePickerDialog.value = false
            }
            endTimePickerDialog.value -> {
                endTime.value = formattedTime
                endTimePickerDialog.value = false
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(8.dp)) {
        Text(
            text = "Add Periods Time",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
        Text("Start Time", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = startTime.value,
            onValueChange = {},
            label = { Text("Select Start Time") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    startTimePickerDialog.value = true
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute -> timePickerListener(hourOfDay, minute) },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                    ).show()
                }
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            enabled = false
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("End Time", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = endTime.value,
            onValueChange = {},
            label = { Text("Select End Time") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    endTimePickerDialog.value = true
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute -> timePickerListener(hourOfDay, minute) },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                    ).show()
                }
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            enabled = false
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Period Duration", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = breakDuration.value,
            onValueChange = {},
            label = { Text("Select Period Duration") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = true }
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            enabled = false
        )
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            breakDurationOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text("$option minutes") },
                    onClick = {
                        breakDuration.value = "$option minutes"
                        onEvent(FormScreenEvents.OnAddPeriodClick(startTime.value, endTime.value, option.toInt()))
                        periodAdded.value = true
                        expanded.value = false
                    }
                )
            }
        }
        if (periodAdded.value)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexed(state.periodList) { index, item ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp) // Adds space between the cards/items
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Period ${index + 1}")
                            Text("Start Time: ${item.startTime}")
                            Text("End Time: ${item.endTime}")
                        }
                    }
                }
            }
    }
}
