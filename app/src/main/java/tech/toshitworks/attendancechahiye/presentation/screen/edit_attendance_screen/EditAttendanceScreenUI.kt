package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.AddExtraAttendanceDialog
import tech.toshitworks.attendancechahiye.presentation.components.edit_attendance.TimetableForEdit
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@Composable
fun EditAttendanceScreen(
    modifier: Modifier,
    viewModel: EditAttendanceScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel
) {
    val homeScreenState by homeScreenViewModel.state.collectAsStateWithLifecycle()
    val homeScreenEvents = homeScreenViewModel::onEvent
    val isAddExtraAttendanceDialogOpen = homeScreenState.isAddExtraAttendanceDialogOpen
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
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
                if (homeScreenState.editAttendanceDate != null) {
                    TimetableForEdit(
                        state = state,
                        onEvent = onEvent,
                        onEditIconClick = {

                        },
                        date = homeScreenState.editAttendanceDate!!,
                        day = DayModel(name = day)
                    )
                }
            }
            if (homeScreenState.isEditAttendanceDatePickerOpen){
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
                ) {sm,date,isPresent->
                    val period = state.periods.find {
                        it.startTime == "empty" && it.endTime == "empty"
                    }!!
                    onEvent(EditAttendanceScreenEvents.OnUpdateAttendance(
                        AttendanceModel(
                            subject = sm,
                            date = date,
                            isPresent = isPresent,
                            period = period
                        )
                    ))
                }
        }
}

