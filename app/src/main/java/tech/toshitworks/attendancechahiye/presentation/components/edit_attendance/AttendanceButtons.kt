package tech.toshitworks.attendancechahiye.presentation.components.edit_attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.presentation.screen.today_attendance_screen.TodayAttendanceScreenEvents

@Composable
fun AttendanceButton(
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