package tech.toshitworks.attendancechahiye.presentation.components.edit_attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.domain.model.TimetableModel

@Composable
fun Details(
    tt: TimetableModel,
    editedSubject: SubjectModel?
) {
    val initials = tt.subject.facultyName.split(" ")
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
    Text(
        text = "Period: ${tt.period.id}",
        style = MaterialTheme.typography.titleLarge
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = tt.subject.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            textDecoration = if (editedSubject != null) TextDecoration.LineThrough else TextDecoration.None
        )
        if (editedSubject != null)
            Text(
                text = editedSubject.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
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
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
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
            )
        }
    }
    Text(
        text = "${tt.period.startTime}-${tt.period.endTime}",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold
        )
    )
}