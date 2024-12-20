package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Calendar
import java.util.Locale

@Composable
fun EditAttendanceScreen(
    modifier: Modifier,
    viewModel: EditAttendanceScreenViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (!state.isLoading)
        Column(
            modifier = modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Edit Attendance : ${if (state.date!=null) state.date else "Date?"}"
                        )
                        IconButton(
                            onClick = {
                                val datePicker = DatePickerDialog(
                                    context,
                                    { _: DatePicker, year: Int, month: Int, day: Int ->
                                        val formattedDate = String.format(Locale.US,"%04d-%02d-%02d",year,month+1,day)
                                        onEvent(EditAttendanceScreenEvents.OnDateSelected(formattedDate))
                                    },
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                )
                                val today = calendar.timeInMillis
                                val endDate = state.endDate
                                val maxDate = if (today > endDate) state.endDate else today
                                datePicker.datePicker.minDate = state.startDate
                                datePicker.datePicker.maxDate = maxDate
                                datePicker.show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "select date"
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(15f)
                    .fillMaxSize()
            ) {

            }
        }
}
