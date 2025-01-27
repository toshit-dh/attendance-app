package tech.toshitworks.attendo.presentation.components.edit_attendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.AttendanceModel
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.NoteModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.domain.model.TimetableModel
import tech.toshitworks.attendo.presentation.components.dialogs.AddNoteDialog
import tech.toshitworks.attendo.presentation.components.dialogs.ChangeSubjectDialog
import tech.toshitworks.attendo.presentation.components.today_attendance.Details
import tech.toshitworks.attendo.presentation.components.today_attendance.PeriodCard
import tech.toshitworks.attendo.presentation.screen.edit_attendance_screen.EditAttendanceScreenEvents
import tech.toshitworks.attendo.presentation.screen.edit_attendance_screen.EditAttendanceScreenStates
import tech.toshitworks.attendo.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents

@Composable
fun TimetableForEdit(
    state: EditAttendanceScreenStates,
    onEditEvent: (EditAttendanceScreenEvents) -> Unit,
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
    val attendanceModelOuter: MutableState<AttendanceModel?> = remember {
        mutableStateOf(null)
    }
    Column {
        LazyColumn {
            items(state.filteredAttendance) {
                val attendanceBySubject = state.attendanceBySubject.find { abs ->
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
                        subject.value = it.subject
                        period.value = it.period
                        attendanceModelOuter.value = it
                        isSubjectChangedDialogOpen.value = true
                    }
                }
            }
        }
        if (addNoteDialogOpen.value) {
            onEditEvent(EditAttendanceScreenEvents.OnNoteClick(attendanceIdOfNote.longValue))
            val note = state.note
            AddNoteDialog(
                note = note,
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
        if (isSubjectChangedDialogOpen.value) {
            val subjectFiltered = state.subjects.filter {
                it != subject.value
            }
            ChangeSubjectDialog(
                subjects = subjectFiltered,
                onDismiss = {
                    isSubjectChangedDialogOpen.value = false
                }
            ) { s ->
                onEditEvent(
                    EditAttendanceScreenEvents.OnUpdateSubject(
                        AttendanceModel(
                            subject = s,
                            period = period.value!!,
                            date = date,
                            isPresent = attendanceModelOuter.value!!.isPresent
                        )
                    )
                )
            }
        }
    }
}

