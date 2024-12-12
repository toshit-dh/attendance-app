package tech.toshitworks.attendancechahiye.presentation.components.home

import CircularProgress
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.AddNoteDialog
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenStates

@Composable
fun TimetableForDay(
    state: TodayAttendanceScreenStates,
    onEvent: (TodayAttendanceScreenEvents) -> Unit,
    onEditIconClick: () -> Unit,
    date: String,
    day: DayModel
) {
    val addNoteDialogOpen = remember {
        mutableStateOf(false)
    }
    val attendanceIdOfNote = remember {
        mutableLongStateOf(0L)
    }
    Column {
        Text(
            text = "Add Attendance: ${day.name}",
            modifier = Modifier.padding(24.dp, 3.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(state.timetableForDay.filter { tm ->
                tm.subject.name != "Lunch" && tm.subject.name != "No Period"
            }
            ) { idx, tt ->
                val subjectAttendance = state.attendanceList.find { abs ->
                    abs.subjectModel.name == tt.subject.name
                }
                val subject = state.attendanceByDate.find { am ->
                    am.subject == tt.subject && tt.period.id == tt.period.id
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .padding(4.dp),
                ) {
                    Box {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Details(tt)
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val lp = subjectAttendance?.lecturesPresent ?: 0
                                val lt = subjectAttendance?.lecturesTaken ?: 0
                                Text(
                                    text = "$lp / $lt",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                CircularProgress(
                                    modifier = Modifier,
                                    percentage = if (lt != 0) ((lp.toFloat() / lt.toFloat()) * 100f) else 0f
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (subject == null || !subject.isPresent)
                                    AttendanceButton("P", subject, onEvent, tt, date,day)
                                Spacer(modifier = Modifier.height(3.dp))
                                if (subject == null || subject.isPresent)
                                    AttendanceButton("A", subject, onEvent, tt, date,day)
                                Spacer(modifier = Modifier.height(3.dp))
                                if (subject!=null)
                                    Row (
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                    ){
                                        IconButton(
                                            onClick = {
                                                addNoteDialogOpen.value = true
                                                attendanceIdOfNote.longValue = subject.id
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Email,
                                                contentDescription = "note"
                                            )
                                        }
                                        IconButton(
                                            onClick = onEditIconClick
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "edit"
                                            )
                                        }
                                    }

                            }
                        }
                    }
                }
            }
        }
        if (addNoteDialogOpen.value) {
            val note = state.notes.find { nm ->
                nm.attendanceId == attendanceIdOfNote.longValue
            }
            val content = remember {
                mutableStateOf(note?.content ?: "")
            }
            AddNoteDialog(
                contentFirst = content,
                onDismiss = {
                    addNoteDialogOpen.value = false
                }
            ) {
                onEvent(
                    TodayAttendanceScreenEvents.OnAddNote(
                        NoteModel(
                            id = note?.id?:0,
                            attendanceId = attendanceIdOfNote.longValue,
                            content = it
                        )
                    )
                )
                addNoteDialogOpen.value = false
            }
        }
    }
}

@Composable
private fun AttendanceButton(
    type: String,
    subject: AttendanceModel?,
    onEvent: (TodayAttendanceScreenEvents) -> Unit,
    tt: TimetableModel,
    date: String,
    day: DayModel
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (type == "P") Color.Green else Color.Red)
            .border(
                4.dp,
                Color.White,
                RoundedCornerShape(16.dp)
            )

    ) {
        IconButton(
            onClick = {
                if (subject == null)
                    onEvent(
                        TodayAttendanceScreenEvents.OnAddAttendance(
                            AttendanceModel(
                                day = day,
                                subject = tt.subject,
                                date = date,
                                isPresent = type == "P",
                                period = tt.period
                            )
                        )
                    )
                else
                    onEvent(
                        TodayAttendanceScreenEvents.OnUpdateAttendance(
                            AttendanceModel(
                                day = day,
                                subject = tt.subject,
                                date = date,
                                isPresent = type == "P",
                                period = tt.period
                            )
                        )
                    )
            },

            ) {
            Text(
                text = if (type == "P") "P" else "A",
                fontWeight = FontWeight.Bold,
                color = Color.White, fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun Details(tt: TimetableModel) {
    val initials = tt.subject.facultyName.split(" ")
    var initial1 = ' '
    var initial2 = ' '
    if (initials.size >= 2) {
        initial1 = initials[0].first()
        initial2 = initials[1].first()
    } else {
        initial1 = initials[0].first()
    }
    Text(
        text = "Period: ${tt.period.id}",
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        text = tt.subject.name,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    )
    Text(
        text = "$initial1$initial2",
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    )
    Text(
        text = "${tt.period.startTime}-${tt.period.endTime}",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold
        )
    )
}