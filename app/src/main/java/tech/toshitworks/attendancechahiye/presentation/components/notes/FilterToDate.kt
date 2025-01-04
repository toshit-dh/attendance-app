package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.WheelTimePicker
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenEvents
import java.time.LocalDate

@Composable
fun FilterToDate(
    startDate: LocalDate,
    minStartDate: String,
    onEvent: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            modifier = Modifier,
            text = "To Date"
        )
        WheelDatePicker(
            startDate = if(startDate!=LocalDate.MAX) startDate else LocalDate.now(),
            minDate = LocalDate.parse(minStartDate),
            maxDate = LocalDate.now()
        ){
            onEvent(it)
        }
    }
}