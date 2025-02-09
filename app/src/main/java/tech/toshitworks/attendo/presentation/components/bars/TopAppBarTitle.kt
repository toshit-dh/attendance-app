package tech.toshitworks.attendo.presentation.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendo.navigation.RoutesArgs
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendo.presentation.screen.home_screen.HomeScreenStates
import java.util.Locale

@Composable
fun TopAppBarTitle(
    state: HomeScreenStates,
    onEvent: (HomeScreenEvents) -> Unit,
    screen: String?
) {
    println(screen)
    val style = MaterialTheme.typography.titleLarge.copy(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    when (screen) {
        ScreenRoutes.TodayAttendance.route -> {
            val text = if (state.dayList.find {
                    it.name == state.todayDay
                } == null) "Enjoy Holiday" else "Add Attendance: ${state.todayDay}"
            Text(
                text = text,
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.AnalyticsScreen.route + RoutesArgs.SubjectIdArg.arg -> {
            if (!state.isLoading) {
                val subject = state.analysisSubject
                val sAttendance = state.attendanceBySubject.find {
                    it.subjectModel == subject
                }
                val isOverall = subject == null
                val lc =
                    if (isOverall) state.attendanceStats?.totalLectures else sAttendance?.lecturesTaken

                val lp =
                    if (isOverall) state.attendanceStats?.totalPresent?:0 else sAttendance?.lecturesPresent?:0
                val percentage = if (lc!=null) String.format(Locale.US, "%.2f", lp.toFloat() * 100 / lc.toFloat()) else "0 "
                Text(
                    text =
                    if (state.analysisSubject != null)
                        "${state.analysisSubject.name}  $lp / ${lc?:0}  $percentage %"
                    else " Overall  $lp / ${lc?:0}  ${if (percentage=="NaN") 0 else percentage} %",
                    style = style,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
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
                text = "Edit Attendance ${state.editAttendanceDate ?: ""}",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.EditInfoScreen.route -> {
            val editInfo = state.editInfo
            val editList = state.editList
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onEvent(HomeScreenEvents.OnEditTypeChange(-1))
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back info"
                    )
                }
                Text(
                    text = editList[editInfo],
                    style = style,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = {
                        onEvent(HomeScreenEvents.OnEditTypeChange(1))
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "forward info"
                    )
                }
            }
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

        ScreenRoutes.SettingsScreen.route -> {
            Text(
                text = "Settings",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        ScreenRoutes.DeleteScreen.route -> {
            Text(
                text = "Delete This Semester",
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}