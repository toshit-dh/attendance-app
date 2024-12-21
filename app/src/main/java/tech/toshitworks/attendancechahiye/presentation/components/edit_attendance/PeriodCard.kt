package tech.toshitworks.attendancechahiye.presentation.components.edit_attendance

import CircularProgress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.AttendanceBySubject
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents

@Composable
fun PeriodCard(
    tt: TimetableModel,
    editedSubject: SubjectModel?,
    subjectAttendance: AttendanceBySubject?,
    editedSubjectAttendance: AttendanceBySubject?,
    attendanceModel: AttendanceModel?,
    onEvent: (TodayAttendanceScreenEvents) -> Unit,
    date: String,
    day: DayModel,
    addNoteDialogOpen: MutableState<Boolean>,
    attendanceIdOfNote: MutableState<Long>,
    onEditIconClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .padding(4.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .height(25.dp)
                    .width(25.dp),
                onClick = onEditIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit"
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Details(tt, editedSubject)
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val lp = subjectAttendance?.lecturesPresent ?: 0
                    val lt = subjectAttendance?.lecturesTaken ?: 0
                    val elp = editedSubjectAttendance?.lecturesPresent ?: 0
                    val elt = editedSubjectAttendance?.lecturesTaken ?: 0
                    Text(
                        text = if (editedSubject != null) "$elp / $elt" else "$lp / $lt",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    CircularProgress(
                        modifier = Modifier,
                        percentage = if (editedSubject != null)
                            if (elt != 0) ((elp.toFloat() / elt.toFloat()) * 100f) else 0f
                        else if (lt != 0) ((lp.toFloat() / lt.toFloat()) * 100f) else 0f
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (attendanceModel == null || !attendanceModel.isPresent)
                        AttendanceButton("P", attendanceModel, onEvent, tt, date, day)
                    Spacer(modifier = Modifier.height(3.dp))
                    if (attendanceModel == null || attendanceModel.isPresent)
                        AttendanceButton("A", attendanceModel, onEvent, tt, date, day)
                    Spacer(modifier = Modifier.height(3.dp))
                    if (attendanceModel != null)
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            IconButton(
                                onClick = {
                                    addNoteDialogOpen.value = true
                                    attendanceIdOfNote.value = attendanceModel.id
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "note"
                                )
                            }
                        }

                }
            }
        }
    }
}