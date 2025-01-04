package tech.toshitworks.attendancechahiye.presentation.components.edit_attendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.components.today_attendance.Details
import tech.toshitworks.attendancechahiye.presentation.components.today_attendance.PeriodCard
import tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen.EditAttendanceScreenStates
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents

@Composable
fun TimetableForEdit(
    state: EditAttendanceScreenStates,
    onEvent: (TodayAttendanceScreenEvents) -> Unit,
    date: String,
    day: DayModel
) {
    val addNoteDialogOpen = remember {
        mutableStateOf(false)
    }
    val attendanceIdOfNote = remember {
        mutableLongStateOf(0L)
    }
    Column{
        LazyColumn {
            items(state.filteredAttendance) {
                val attendanceBySubject = state.attendanceBySubject.find {abs->
                    abs.subjectModel == it.subject
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(145.dp)
                        .padding(4.dp),
                ) {
                    PeriodCard(
                        tt = TimetableModel(
                            subject = it.subject!!,
                            day = it.day!!,
                            period = it.period
                        ),
                        attendanceBySubject,
                        it,
                        onEvent,
                        date,
                        day,
                        addNoteDialogOpen,
                        attendanceIdOfNote,
                        it.deleted,
                        {
                            Details(
                                TimetableModel(
                                    subject = it.subject,
                                    day = it.day,
                                    period = it.period
                                ),
                                it,
                                null
                            )
                        }
                    ) {

                    }
//                    Box(
//                        modifier = Modifier
//                            .padding(4.dp)
//                    ) {
//                        IconButton(
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .height(25.dp)
//                                .width(25.dp),
//                            onClick = onEditIconClick
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Edit,
//                                contentDescription = "edit"
//                            )
//                        }
//                        Row(
//                            modifier = Modifier
//                                .padding(8.dp)
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .fillMaxHeight(),
//                                verticalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Details(
//                                    TimetableModel(
//                                        subject = it.subject!!,
//                                        day = it.day!!,
//                                        period = it.period
//                                    )
//                                )
//                            }
//                            Column(
//                                modifier = Modifier
//                                    .weight(1f),
//                                verticalArrangement = Arrangement.SpaceBetween,
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//
////                                val lp = subjectAttendance?.lecturesPresent ?: 0
////                                val lt = subjectAttendance?.lecturesTaken ?: 0
////                                Text(
////                                    text = "$lp / $lt",
////                                    fontWeight = FontWeight.Bold,
////                                    fontSize = 20.sp
////                                )
////                                CircularProgress(
////                                    modifier = Modifier,
////                                    percentage = if (lt != 0) ((lp.toFloat() / lt.toFloat()) * 100f) else 0f
////                                )
//                            }
//                            Column(
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .fillMaxHeight(),
//                                verticalArrangement = Arrangement.SpaceBetween,
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                val tt = TimetableModel(
//                                    subject = it.subject!!,
//                                    day = it.day!!,
//                                    period = it.period
//                                )
//                                if (!it.isPresent)
//                                    AttendanceButton("P", onEvent, tt, date, day)
//                                Spacer(modifier = Modifier.height(3.dp))
//                                if (it.isPresent)
//                                    AttendanceButton("A",onEvent, tt, date, day)
//                                Spacer(modifier = Modifier.height(3.dp))
//                                Row(
//                                    modifier = Modifier
//                                        .clip(RoundedCornerShape(16.dp))
//                                ) {
//                                    IconButton(
//                                        onClick = {
//                                            addNoteDialogOpen.value = true
//                                            attendanceIdOfNote.longValue = it.id
//                                        }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Default.Email,
//                                            contentDescription = "note"
//                                        )
//                                    }
//                                }
//
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

