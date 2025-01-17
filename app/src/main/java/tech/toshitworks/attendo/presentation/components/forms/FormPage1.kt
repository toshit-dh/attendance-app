package tech.toshitworks.attendo.presentation.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import tech.toshitworks.attendo.domain.model.SemesterModel
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreen1Events

@Composable
fun FormPage1(
    state: SemesterModel?,
    onEvent: (event: FormScreen1Events) -> Unit
) {
    val setLaterMidTerm = remember {
        mutableStateOf(false)
    }
    val setLaterEndDate = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Semester Number : ",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
                value = state?.semNumber?.toString() ?: "",
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        println(newValue)
                        onEvent(FormScreen1Events.OnSemesterNumberChange(newValue))
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Start\nDate: ",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            WheelDatePicker {
                onEvent(FormScreen1Events.OnStartDateChange(it.toString()))
            }
        }
        HorizontalDivider()
        Column{
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Mid Term Date : ",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (setLaterMidTerm.value)
                    Text(text = "Set Later")
                else
                    WheelDatePicker {
                        onEvent(FormScreen1Events.OnMidTermDateChange(it.toString()))
                    }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = setLaterMidTerm.value,
                    onCheckedChange = {
                        if (it) onEvent(FormScreen1Events.OnMidTermDateChange(null))
                        setLaterMidTerm.value = it
                    }
                )
                Text(text = "Set Later")
            }
        }
        HorizontalDivider()
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Term End Date : ",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (setLaterEndDate.value)
                    Text(text = "Set Later")
                else
                    WheelDatePicker {
                        onEvent(FormScreen1Events.OnEndDateChange(null))
                    }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = setLaterEndDate.value,
                    onCheckedChange = {
                        if (it) onEvent(FormScreen1Events.OnEndDateChange(""))
                        setLaterEndDate.value = it
                    }
                )
                Text(text = "Set Later")
            }
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "info"
            )
            Text(
                text = "Mid Term Date and Term End Date can be filled later. "
            )
        }
    }
}
