package tech.toshitworks.attendancechahiye.presentation.components.bars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenStates
import java.util.Locale

@Composable
fun TopAppBarTitle(
    state: HomeScreenStates,
    screen: String?
){
    val style = MaterialTheme.typography.titleLarge.copy(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    when (screen) {
        ScreenRoutes.TodayAttendance.route -> {
            Text(
                text = "Add Attendance: ${state.todayDay}",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.AnalyticsScreen.route -> {
            if (!state.isLoading) {
                val subject = state.analysisSubject
                val sAttendance = state.attendanceBySubject.find {
                    it.subjectModel == subject
                }
                val isOverall = subject == null
                val lc = if (isOverall) state.attendanceStats!!.totalLectures else sAttendance!!.lecturesTaken
                val lp = if (isOverall) state.attendanceStats!!.totalPresent else sAttendance!!.lecturesPresent
                val percentage = String.format(Locale.US,"%.2f",lp.toFloat()*100/lc.toFloat())
                Text(
                    text =
                    if (state.analysisSubject != null)
                        "${state.analysisSubject.name}  $lp / $lc  $percentage %"
                    else " Overall  $lp / $lc  $percentage %",
                    style = style,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }else {
                Text(
                    text = "Overall  ? / ?  ?%",
                    style = style,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        ScreenRoutes.EditAttendanceScreen.route -> {
            Text(
                text = "Edit Attendance: ${state.editAttendanceDate ?: ""}",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.EditInfoScreen.route -> {
            Text(
                text = "Edit Info",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.NotesScreen.route -> {
            Text(
                text = "Notes",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.EventsScreen.route -> {
            Text(
                text = "Events",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.ExportScreen.route -> {
            Text(
                text = "Export (.csv)",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.NotificationScreen.route -> {
            Text(
                text = "Notifications",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}