package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendancechahiye.domain.model.EligibilityData

@Composable
fun EligibilityAnalysis(
    eligibilityOfMidterm: EligibilityData?,
    eligibilityOfEndSem: EligibilityData?
) {
    val midTermDateProvided = eligibilityOfMidterm != null
    val endSemDateProvided = eligibilityOfEndSem != null

    Row (
        modifier = Modifier
            .fillMaxSize(),
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Mid Term",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            if (midTermDateProvided)
                MidTermText(eligibilityOfMidterm!!)
            else
                Text(
                    text = "No Date Provided",
                )
        }
        VerticalDivider(thickness = 5.dp)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "End Sem",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            if (endSemDateProvided)
                EndSemText(eligibilityOfEndSem!!)
            else
                Text(
                    text = "No Date Provided"
                )
        }
    }

}

@Composable
private fun MidTermText(
    eligibilityOfMidterm: EligibilityData
) {
    Text(
        text = if (eligibilityOfMidterm.isEligibleForNow) "Eligible" else "Not Eligible"
    )
    if (eligibilityOfMidterm.isEligibleForNow)
        Text(
            text = "Can Bunk ${eligibilityOfMidterm.bunkLectures} lectures"
        )
    else if (eligibilityOfMidterm.isEligibleInFuture) {
        Text(
            text = "Eligible in Future If Attend ${eligibilityOfMidterm.moreLectures} lectures and bunk ${eligibilityOfMidterm.bunkLectures} lectures"
        )

    }
}

@Composable
private fun EndSemText(
    eligibilityOfEndSem: EligibilityData
) {
    Text(
        text = if (eligibilityOfEndSem.isEligibleForNow) "Eligible" else "Not Eligible"
    )
    if (eligibilityOfEndSem.isEligibleForNow)
        Text(
            text = "Can Bunk ${eligibilityOfEndSem.bunkLectures} lectures"
        )
    else if (eligibilityOfEndSem.isEligibleInFuture) {
        Text(
            text = "Eligible in Future If Attend ${eligibilityOfEndSem.moreLectures} lecture and bunk ${eligibilityOfEndSem.bunkLectures} lectures"
        )
    }
}