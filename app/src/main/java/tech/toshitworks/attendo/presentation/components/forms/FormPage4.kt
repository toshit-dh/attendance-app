package tech.toshitworks.attendo.presentation.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenEvents

@Composable
fun FormPage4(
    periodList: List<PeriodModel>,
    onEvent: (event: FormScreenEvents) -> Unit
) {
    val periodAdded = remember {
        mutableStateOf(false)
    }
    val startTime = remember {
        mutableStateOf("")
    }
    val endTime = remember {
        mutableStateOf("")
    }
    val breakDuration = remember {
        mutableStateOf("Select Period Duration") }
    val breakDurationOptions = listOf("5", "10", "15", "20", "30", "45", "60")
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Start Time",
                    fontSize = 18.sp,
                )
                WheelTimePicker {
                    startTime.value = it.toString()
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "End Time",
                    fontSize = 18.sp,
                )
                WheelTimePicker {
                    endTime.value = it.toString()
                }
            }
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Break Duration : ",
                fontSize = 18.sp,
            )
            TextField(
                value = breakDuration.value,
                onValueChange = {},
                modifier = Modifier
                    .clickable { expanded.value = true }
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors().copy(
                    disabledTextColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true,
                enabled = false
            )
        }
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            breakDurationOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text("$option minutes") },
                    onClick = {
                        breakDuration.value = "$option minutes"
                        onEvent(
                            FormScreenEvents.OnAddPeriodClick(
                                startTime.value,
                                endTime.value,
                                option.toInt()
                            )
                        )
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
                itemsIndexed(periodList) { index, item ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                text = "Period ${index + 1}"
                            )
                            Text(
                                text = "Start Time: ${item.startTime}"
                            )
                            Text(
                                text = "End Time: ${item.endTime}"
                            )
                        }
                    }
                }
            }
    }
}
