package tech.toshitworks.attendo.presentation.screen.edit_attendance_screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.presentation.components.dialogs.AddExtraAttendanceDialog
import tech.toshitworks.attendo.presentation.components.edit_attendance.TimetableForEdit
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenEvents
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@Composable
fun EditAttendanceScreen(
    modifier: Modifier,
    viewModel: EditAttendanceScreenViewModel,
    isAddExtraAttendanceDialogOpen: Boolean,
    isEditAttendanceDatePickerOpen: Boolean,
    editAttendanceDate: String?,
    homeScreenEvents: (HomeScreenEvents)->Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val attendanceOnEvent = viewModel::attendanceEvent
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val day =  try {
        LocalDate.parse(state.date, formatter).dayOfWeek.getDisplayName(
            java.time.format.TextStyle.FULL,
            Locale.getDefault()
        )
    }catch (e: Exception){
        ""
    }
    if (!state.isLoading)
        Column(
            modifier = modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(10f)
            ) {
                if (editAttendanceDate != null) {
                    TimetableForEdit(
                        state = state,
                        onEvent = attendanceOnEvent,
                        date = editAttendanceDate,
                        day = DayModel(name = day)
                    )
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_tasks),
                            contentDescription = "image"
                        )
                        Text(
                            modifier = Modifier
                                .padding(35.dp),
                            text = "Select a date to edit attendance by clicking calendar icon or click + button to add extra attendance",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
            if (isEditAttendanceDatePickerOpen){
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
                        homeScreenEvents(
                            HomeScreenEvents.OnEditAttendanceDateSelected(formattedDate)
                        )
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
            if (isAddExtraAttendanceDialogOpen)
                AddExtraAttendanceDialog(
                    startDate = state.startDate,
                    endDate = state.endDate,
                    subjectList = state.subjects,
                    onDismiss = {
                        homeScreenEvents(HomeScreenEvents.OnAddExtraAttendanceClick)
                    }
                ) {sm,date,day,isPresent->
                    val period = state.periods.find {
                        it.startTime == "empty" && it.endTime == "empty"
                    }!!
                    onEvent(EditAttendanceScreenEvents.OnAddExtraAttendance(
                        AttendanceModel(
                            day = DayModel(name = day),
                            subject = sm,
                            date = date,
                            isPresent = isPresent,
                            period = period
                        )
                    ))
                    homeScreenEvents(HomeScreenEvents.OnAddExtraAttendanceClick)
                }
        }
    else
        LoadingIndicator(modifier)
}

