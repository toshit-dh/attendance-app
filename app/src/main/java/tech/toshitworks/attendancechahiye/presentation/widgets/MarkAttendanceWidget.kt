package tech.toshitworks.attendancechahiye.presentation.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.provideContent
import dagger.hilt.android.EntryPointAccessors
import tech.toshitworks.attendancechahiye.R
import tech.toshitworks.attendancechahiye.di.WidgetEntryPoint

class MarkAttendanceWidgetReceiver: GlanceAppWidgetReceiver(){
    override val glanceAppWidget: GlanceAppWidget = MarkAttendanceWidget()
}


class MarkAttendanceWidget: GlanceAppWidget(
    errorUiLayout = R.layout.attendance_stats_error
) {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MarkAttendanceUI(context = context)
        }
    }
}

@Composable
fun MarkAttendanceUI(context: Context) {
    val widgetRepo = EntryPointAccessors.fromApplication(context, WidgetEntryPoint::class.java).subjectRepository()
    LazyColumn {

    }
}