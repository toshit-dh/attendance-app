package tech.toshitworks.attendo.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.AttendanceStats
import java.util.Locale

@Composable
fun AttendanceByDateRange(
    modifier: Modifier,
    fromDate: MutableState<String?>,
    isDatePickerOpen: MutableState<Pair<Boolean, String>>,
    toDate: MutableState<String?>,
    attendanceStats: MutableState<AttendanceStats?>
) {
    Card (
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (fromDate.value != null) fromDate.value!! else "From Date: ?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = {
                        isDatePickerOpen.value = Pair(true, "from")

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "from date"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (toDate.value != null) toDate.value!! else "To Date: ?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = {
                        isDatePickerOpen.value = Pair(true, "to")

                    },
                    enabled = fromDate.value != null
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "to date"
                    )
                }
            }
            val aS = attendanceStats.value == null
            val totalPresent = if (aS) "?" else attendanceStats.value!!.totalPresent.toString()
            val totalLectures =
                if (aS) "?" else attendanceStats.value!!.totalLectures.toString()
            val percentage = if (totalPresent == "?") "?"
            else String.format(
                Locale.US, "%.2f",
                attendanceStats.value!!.totalPresent.toFloat() * 100 / attendanceStats.value!!.totalLectures.toFloat()
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = "$totalPresent/$totalLectures - $percentage%",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}