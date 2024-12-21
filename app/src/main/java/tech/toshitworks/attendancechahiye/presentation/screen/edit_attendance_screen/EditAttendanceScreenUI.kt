package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import CircularProgress
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.presentation.components.edit_attendance.TimetableForEdit
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(165.dp),
                ) {
                    Text(
                        text = "Overall Attendance",
                        modifier = Modifier.padding(8.dp, 3.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${state.attendanceStats.totalPresent} / ${state.attendanceStats.totalLectures}",
                            fontSize = 20.sp
                        )
                        CircularProgress(
                            modifier = Modifier.weight(1f),
                            percentage = state.attendanceStats.attendancePercentage.toFloat()
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Card(
                        modifier = Modifier
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
                                text = "Edit Attendance : ${if (state.date != null) state.date else "Date?"}"
                            )
                            IconButton(
                                onClick = {
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
                                            onEvent(
                                                EditAttendanceScreenEvents.OnDateSelected(
                                                    formattedDate
                                                )
                                            )
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
            Box(modifier = Modifier
                .weight(10f)){
                if (state.date != null) {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val day = LocalDate.parse(state.date!!,formatter).dayOfWeek.getDisplayName(
                        java.time.format.TextStyle.FULL,
                        Locale.getDefault()
                    )
                    TimetableForEdit(
                        state = state,
                        onEvent = onEvent,
                        onEditIconClick = {

                        },
                        date = state.date!!,
                        day = DayModel(name = day)
                    )
                }}
            }
        }

