package tech.toshitworks.attendancechahiye.presentation.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import dagger.hilt.android.EntryPointAccessors
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
private fun AttendanceStatsUI(context: Context) {
    val widgetRepo = EntryPointAccessors.fromApplication(context, WidgetEntryPoint::class.java)
    val attendanceRepo = widgetRepo.attendanceRepository()
    val stats by attendanceRepo.getAttendancePercentage().collectAsState(AttendanceStats(0,0,0.0))
    if (stats.attendancePercentage != 0.0) {
        val overall = stats.attendancePercentage
        val taken = stats.totalLectures
        val present = stats.totalPresent
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .background(Color(0xFFF8DCDE)),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = GlanceModifier
                        .padding(12.dp),
                    text = "Overall Attendance",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            Row(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$present / $taken",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
                Spacer(modifier = GlanceModifier.width(20.dp))
                Text(
                    text = """${String.format(
                        Locale.US,
                        "%.2f",
                        overall
                    )} % """,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }

        }
    }
}
