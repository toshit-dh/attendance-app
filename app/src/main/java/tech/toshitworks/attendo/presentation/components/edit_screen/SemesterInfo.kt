package tech.toshitworks.attendo.presentation.components.edit_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import tech.toshitworks.attendo.domain.model.SemesterModel
import java.time.LocalDate

@Composable
fun SemesterInfo(
    semesterModel: SemesterModel,
) {
    val startDateLocal = LocalDate.parse(semesterModel.startDate)
    val midTermDateLocal = LocalDate.parse(semesterModel.midTermDate)
    val endDateLocal = LocalDate.parse(semesterModel.endDate)
    val semNumber = remember {
        mutableIntStateOf(semesterModel.semNumber)
    }
    val startDate = remember {
        mutableStateOf(startDateLocal)
    }
    val midTermDate = remember {
        mutableStateOf(midTermDateLocal)
    }
    val endDate = remember {
        mutableStateOf(endDateLocal)
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
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
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            label = {
                Text(
                    text = "Add Semester Number",
                    fontSize = 20.sp
                )
            },
            value = semNumber.intValue.toString(),
            onValueChange = {
                semNumber.intValue = it.toIntOrNull() ?: 0
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Start Date",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            WheelDatePicker(
                startDate = startDateLocal
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1.5f),
                text = "Mid Term Date",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            WheelDatePicker(
                startDate = midTermDateLocal,
                minDate = startDateLocal
            ){
                midTermDate.value = it
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "End Date",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            WheelDatePicker(
                startDate = endDateLocal,
                minDate = midTermDateLocal
            ){
                endDate.value = it
            }
        }
    }
}