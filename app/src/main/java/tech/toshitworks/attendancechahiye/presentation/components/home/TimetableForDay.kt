package tech.toshitworks.attendancechahiye.presentation.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.AddNoteDialog
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.ChangeSubjectDialog
import tech.toshitworks.attendancechahiye.presentation.components.edit_attendance.PeriodCard
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenStates

@Composable
fun TimetableForDay(
    state: TodayAttendanceScreenStates,
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
    val isSubjectChangedDialogOpen = remember {
        mutableStateOf(false)
    }
    val subject: MutableState<SubjectModel?> = remember {
        mutableStateOf(null)
    }
    val period: MutableState<PeriodModel?> = remember {
        mutableStateOf(null)
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
            items(state.timetableForDay.filter { tm ->
                tm.subject.name != "Lunch" && tm.subject.name != "No Period"
            }
            ) { tt ->
                val subjectAttendance = state.attendanceList.find { abs ->
                    abs.subjectModel.name == tt.subject.name
                }
                val attendanceModel = state.attendanceByDate.find { am ->
                    am.subject == tt.subject && tt.period.id == tt.period.id
                }
                val editedDateSubject = tt.editedForDates?.find {
                    it.split(':')[0] == date
                }
                val editedSubjectId = editedDateSubject?.split(':')?.get(1)?.toLong()?:-1
                val editedSubject = state.subjectList.find {
                    it.id == editedSubjectId
                }
                val editedSubjectAttendance = state.attendanceList.find {
                    it.subjectModel.name == editedSubject?.name
                }
                PeriodCard(
                    tt,
                    editedSubject,
                    subjectAttendance,
                    editedSubjectAttendance,
                    attendanceModel,
                    onEvent,
                    date,
                    day,
                    addNoteDialogOpen,
                    attendanceIdOfNote
                ){
                    subject.value = tt.subject
                    period.value = tt.period
                    isSubjectChangedDialogOpen.value = true
                }
            }
        }
        if (isSubjectChangedDialogOpen.value)
            ChangeSubjectDialog(
                subjects = state.subjectList.filter {
                    it != subject.value
                },
                onDismiss = {
                    isSubjectChangedDialogOpen.value = false
                }
            ) { s ->
                onEvent(TodayAttendanceScreenEvents.OnUpdatePeriod(
                    TimetableModel(
                        subject = s,
                        day = state.day!!,
                        period = period.value!!
                    )
                ))
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
                            id = note?.id ?: 0,
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
