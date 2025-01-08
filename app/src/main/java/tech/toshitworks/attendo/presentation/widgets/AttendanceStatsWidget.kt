package tech.toshitworks.attendo.presentation.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendo.MainActivity
import tech.toshitworks.attendo.R
import tech.toshitworks.attendo.di.RepoEntryPoint
import tech.toshitworks.attendo.domain.model.AttendanceStats
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
    val widgetRepo = EntryPointAccessors.fromApplication(context, RepoEntryPoint::class.java)
    val attendanceRepo = widgetRepo.attendanceRepository()
    val overallState =
        attendanceRepo.getAttendancePercentage().collectAsState(AttendanceStats(-1, -1, 0.0))
    val overall = overallState.value
    val statsState = attendanceRepo.getAttendancePercentageBySubject().collectAsState(emptyList())
    val stats = statsState.value

        Box(
            modifier = GlanceModifier
                .clickable(
                    onClick = actionStartActivity<MainActivity>()
                )
                .background(Color(0xFF1D0F11))
                .padding(12.dp),
        ) {
            if (stats.isNotEmpty() && overall.totalPresent != -1)
            Column(
                    modifier = GlanceModifier
                        .fillMaxSize(),
                ) {
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
                                fontWeight = FontWeight.Bold,
                                color = ColorProvider(Color(0xFFFFB2BC))
                            )
                        )
                    }
                    Row(
                        modifier = GlanceModifier
                            .fillMaxWidth()
                    ) {
                        val totalPresent = overall.totalPresent
                        val totalLectures = overall.totalLectures
                        val formattedTotalPresent = String.format(Locale.US,"%02d", totalPresent)
                        val formattedTotalLectures = String.format(Locale.US,"%02d", totalLectures)
                        val attendancePercentage = String.format(Locale.US, "%.2f", overall.attendancePercentage)
                        LazyColumn(
                            modifier = GlanceModifier
                                .defaultWeight()
                        ) {
                            item {
                                Text(
                                    text = "Subjects",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                            item {
                                Text(
                                    text = "Overall: ",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                            items(stats){
                                val subjectName = it.subjectModel.name
                                Text(
                                    text = "$subjectName: ",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                        }
                        LazyColumn(
                            modifier = GlanceModifier
                                .defaultWeight()
                        ) {
                            item {
                                Text(
                                    text = "Lectures",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                            item {
                                Text(
                                    text = "$formattedTotalPresent / $formattedTotalLectures",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                            items(stats){
                                val lecturesPresent = it.lecturesPresent
                                val lecturesTaken = it.lecturesTaken
                                val formattedTotalPresentS = String.format(Locale.US,"%02d", lecturesPresent)
                                val formattedTotalLecturesS = String.format(Locale.US,"%02d", lecturesTaken)
                                Text(
                                    text = "$formattedTotalPresentS / $formattedTotalLecturesS",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                        }
                        LazyColumn (
                            modifier = GlanceModifier
                                .defaultWeight()
                        ){
                            item {
                                Text(
                                    text = "Percent",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                            item {
                                Text(
                                    text = "$attendancePercentage %",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                            items(stats){
                                val percentage = String.format(
                                    Locale.US,
                                    "%05.2f",
                                    it.lecturesPresent.toFloat() * 100.0 / it.lecturesTaken.toFloat()
                                )
                                Text(
                                    text = "$percentage %",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ColorProvider(Color(0xFFFFB2BC))
                                    )
                                )
                            }
                        }
                    }
                }
            else if (stats.isEmpty())
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "No Data Found. Add data to see the stats",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color(0xFFFFB2BC))
                        )
                    )
                }
        }

}



