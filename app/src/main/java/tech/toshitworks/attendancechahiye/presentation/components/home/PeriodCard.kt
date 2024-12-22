package tech.toshitworks.attendancechahiye.presentation.components.home

import CircularProgress
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import tech.toshitworks.attendancechahiye.domain.model.AttendanceBySubject
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents

@Composable
fun PeriodCard(
    tt: TimetableModel,
    subjectAttendance: AttendanceBySubject?,
    attendanceModel: AttendanceModel?,
    onEvent: (TodayAttendanceScreenEvents) -> Unit,
    date: String,
    day: DayModel,
    addNoteDialogOpen: MutableState<Boolean>,
    attendanceIdOfNote: MutableState<Long>,
    deleted: Boolean,
    details: @Composable ()->Unit,
    onEditIconClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .padding(4.dp),
        colors = if (deleted) CardColors(
            containerColor = MaterialTheme.colorScheme.onError,
            contentColor = Color.Gray,
            disabledContainerColor = MaterialTheme.colorScheme.onError,
            disabledContentColor = Color.Black
        ) else CardDefaults.cardColors()
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
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(y = 40.dp)
                    .height(25.dp)
                    .width(25.dp),
                onClick = {
                    onEvent(TodayAttendanceScreenEvents.OnDeletePeriod(
                        tt,
                        AttendanceModel(
                            day = day,
                            subject = tt.subject,
                            period = tt.period,
                            date = date,
                            isPresent = attendanceModel?.isPresent ?: false,
                            deleted = true
                        ),
                        true
                    ))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete"
                )
            }
            if (deleted)
                IconButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(1f)
                        .border(3.dp, Color.White, RoundedCornerShape(16.dp)),
                    onClick = {
                        onEvent(TodayAttendanceScreenEvents.OnDeletePeriod(
                            tt,
                            AttendanceModel(
                                day = day,
                                subject = tt.subject,
                                period = tt.period,
                                date = date,
                                isPresent = attendanceModel?.isPresent ?: false,
                                deleted = false
                            ),
                            false)
                        )
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp),
                        imageVector = Icons.Default.Restore,
                        tint = Color.Red,
                        contentDescription = "delete"
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
                    details()
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
                        text =  "$lp / $lt",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    if (!deleted)
                        CircularProgress(
                            modifier = Modifier,
                            percentage = if (lt != 0) ((lp.toFloat() / lt.toFloat()) * 100f) else 0f
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
                        AttendanceButton("P", attendanceModel, onEvent, tt, date, day,deleted)
                    Spacer(modifier = Modifier.height(3.dp))
                    if (attendanceModel == null || attendanceModel.isPresent)
                        AttendanceButton("A", attendanceModel, onEvent, tt, date, day,deleted)
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