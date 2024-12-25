package tech.toshitworks.attendancechahiye.presentation.components.today_attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.AttendanceModel
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel
import tech.toshitworks.attendancechahiye.utils.colors

@Composable
fun Details(
    tm: TimetableModel,
    am: AttendanceModel?,
    editedSubject: SubjectModel?
) {
    val random = colors.random()
    val initials = tm.subject.facultyName.split(" ")
    var initial1 = ' '
    var initial2 = ' '
    var editedInitial = ' '
    var editedInitial2 = ' '
    if (initials.size >= 2) {
        initial1 = initials[0].first()
        initial2 = initials[1].first()
    } else {
        initial1 = initials[0].first()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = tm.subject.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            color = random,
            textDecoration = if (editedSubject != null) TextDecoration.LineThrough else TextDecoration.None
        )
        if (editedSubject != null)
            Text(
                text = editedSubject.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                ),
                color = random
            )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$initial1$initial2",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            ),
            color = random,
            textDecoration = if (editedSubject != null) TextDecoration.LineThrough else TextDecoration.None
        )
        if (editedSubject != null) {
            val editedInitials = editedSubject.facultyName.split(" ")
            if (editedInitials.size >= 2) {
                editedInitial = editedInitials[0].first()
                editedInitial2 = editedInitials[1].first()
            } else {
                editedInitial = editedInitials[0].first()
            }
            Text(
                text = "$editedInitial$editedInitial2",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = random
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Period: ${tm.period.id}",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        if (am != null)
            Icon(
                tint = if (am.isPresent) Color.Green else Color.Red,
                imageVector = if (am.isPresent) Icons.Default.Check else Icons.Default.Close,
                contentDescription = "tick"
            )
    }
    Text(
        text = "${tm.period.startTime}-${tm.period.endTime}",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold
        )
    )
}