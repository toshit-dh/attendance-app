package tech.toshitworks.attendancechahiye.presentation.components.dialogs

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Locale

@Composable
fun PickDateDialog(
    modifier: Modifier = Modifier,
    minDate: Long,
    maxDate: Long,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val context = LocalContext.current
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
            onDateSelected(formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    )
    datePicker.datePicker.minDate = minDate
    datePicker.datePicker.maxDate = maxDate
    datePicker.show()
}