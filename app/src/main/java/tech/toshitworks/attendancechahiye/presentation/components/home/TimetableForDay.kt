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
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.NoteModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.AddNoteDialog
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.ChangeSubjectDialog
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
    val editedSubjectOuter: MutableState<SubjectModel?> = remember {
        mutableStateOf(null)
    }
    val attendanceModelOuter: MutableState<AttendanceModel?> = remember {
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
            ) { tm ->
                val subjectAttendance = state.attendanceList.find { abs ->
                    abs.subjectModel.name == tm.subject.name
                }
                val editedDateSubject = tm.editedForDates?.find {
                    it.split(':')[0] == date
                }
                val editedSubjectId = editedDateSubject?.split(':')?.get(1)?.toLong() ?: -1
                val editedSubject = state.subjectList.find {
                    it.id == editedSubjectId
                }
                val editedSubjectAttendance = state.attendanceList.find {
                    it.subjectModel.name == editedSubject?.name
                }
                val attendanceModel = state.attendanceByDate.find { am ->
                    val subjectOfAttendance = editedSubject ?: tm.subject
                    am.subject == subjectOfAttendance && am.period.id == tm.period.id
                }
                val deleted = tm.deletedForDates?.contains(date) == true
                PeriodCard(
                    if (editedSubject != null) tm.copy(
                        subject = editedSubject
                    ) else tm,
                    if (editedSubject != null) editedSubjectAttendance else subjectAttendance,
                    attendanceModel,
                    onEvent,
                    date,
                    day,
                    addNoteDialogOpen,
                    attendanceIdOfNote,
                    deleted,
                    {
                        Details(tm, editedSubject)
                    }
                ) {
                    if (editedSubject != null) editedSubjectOuter.value = editedSubject
                    subject.value = tm.subject
                    period.value = tm.period
                    attendanceModelOuter.value = attendanceModel
                    isSubjectChangedDialogOpen.value = true
                }
            }
        }
        if (isSubjectChangedDialogOpen.value) {
            val subjectFiltered = if (editedSubjectOuter.value != null) state.subjectList.filter {
                it != editedSubjectOuter.value
            } else state.subjectList.filter {
                it != subject.value
            }
            ChangeSubjectDialog(
                subjects = subjectFiltered,
                onDismiss = {
                    isSubjectChangedDialogOpen.value = false
                }
            ) { s ->
                onEvent(
                    TodayAttendanceScreenEvents.OnUpdatePeriod(
                        TimetableModel(
                            subject = s,
                            day = state.day!!,
                            period = period.value!!,
                        ),
                        isPresent = attendanceModelOuter.value?.isPresent
                    )
                )
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
