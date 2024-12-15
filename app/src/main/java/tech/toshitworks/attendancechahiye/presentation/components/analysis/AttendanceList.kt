package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen.AnalyticsScreenEvents
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceList(
    onEvent: KFunction1<AnalyticsScreenEvents, Unit>,
    isSheetOpen: Boolean,
    subject: SubjectModel?,
    attendanceList: List<AttendanceModel>,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {
    onEvent(AnalyticsScreenEvents.OnChangeSubject(subject?.id?:0))
    if (isSheetOpen)
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val text = subject?.name?:"Overall"
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = text,
                    style = MaterialTheme.typography.titleLarge
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    itemsIndexed(attendanceList) {idx,it->
                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.subject!!.name,
                                    color = colors[idx % colors.size]
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.date
                                )
                                Text(
                                    text = if (it.isPresent) "Present" else "Absent",
                                    color = if (it.isPresent) Color.Green else Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
}