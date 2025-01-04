package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commandiron.wheel_picker_compose.WheelDatePicker
import java.time.LocalDate

@Composable
fun FilterFromDate(
    startDate: LocalDate,
    minStartDate: String?,
    onEvent: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            modifier = Modifier,
            text = "From Date"
        )

        WheelDatePicker(
            startDate = if (startDate!=LocalDate.MIN) startDate else LocalDate.parse(minStartDate),
            minDate = LocalDate.parse(minStartDate)
        ){
            onEvent(it)
        }
    }
}