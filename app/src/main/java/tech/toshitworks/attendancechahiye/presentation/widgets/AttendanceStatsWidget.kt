package tech.toshitworks.attendancechahiye.presentation.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.toshitworks.attendancechahiye.MainActivity
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.di.WidgetEntryPoint
import tech.toshitworks.attendancechahiye.domain.model.AttendanceStats
import java.util.Locale


class AttendanceStatsWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AttendanceStatsWidget()
}

class AttendanceStatsWidget : GlanceAppWidget(
    errorUiLayout = R.layout.attendance_stats_error
) {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            AttendanceStatsUI(context = context)
        }
    }

}


@Composable
private fun AttendanceStatsUI(
    context: Context,
) {
    val widgetRepo = EntryPointAccessors.fromApplication(context, WidgetEntryPoint::class.java)
    val attendanceRepo = widgetRepo.attendanceRepository()
    val overallState =
        attendanceRepo.getAttendancePercentage().collectAsState(AttendanceStats(-1, -1, 0.0))
    val overall = overallState.value
    val statsState = attendanceRepo.getAttendancePercentageBySubject().collectAsState(emptyList())
    val stats = statsState.value
    if (stats.isNotEmpty() && overall.totalPresent != -1) {
        Box(
            modifier = GlanceModifier
                .clickable(
                    onClick = actionStartActivity<MainActivity>()
                )
                .background(Color(0xFFFFB2BC))
                .padding(12.dp),
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.Start
            ) {
                val totalPresent = overall.totalPresent
                val totalLectures = overall.totalLectures
                val attendancePercentage =
                    String.format(Locale.US, "%.2f", overall.attendancePercentage)
                item {
                    Row(
                        modifier = GlanceModifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Attendance",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
                item {
                    Row(
                        modifier = GlanceModifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overall: ",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(4.dp))
                        Text(
                            text = "$totalPresent / $totalLectures",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(4.dp))
                        Text(
                            text = "$attendancePercentage %",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Spacer(modifier = GlanceModifier.height(8.dp))
                }
                items(stats) {
                    val subjectName = it.subjectModel.name
                    val lecturesPresent = it.lecturesPresent
                    val lecturesTaken = it.lecturesTaken
                    val percentage = String.format(
                        Locale.US,
                        "%.2f",
                        it.lecturesPresent.toFloat() * 100.0 / it.lecturesTaken.toFloat()
                    )
                    Row(
                        modifier = GlanceModifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$subjectName: ",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(4.dp))
                        Text(
                            text = "$lecturesPresent / $lecturesTaken",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(4.dp))
                        Text(
                            text = "$percentage %",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Spacer(modifier = GlanceModifier.height(6.dp))
                }
            }
//            Image(
//                modifier = GlanceModifier
//                    .clickable(
//                        onClick = actionRunCallback(UpdateDataCallBack::class.java)
//                    ),
//                provider = ImageProvider(R.drawable.reload),
//                contentDescription = "load state"
//            )
        }
    }
}



