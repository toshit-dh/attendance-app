package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EligibilityAnalysis(
    eligibilityOfMidterm: Pair<Pair<Int, Int>, Boolean>?,
    eligibilityOfEndSem: Pair<Pair<Int, Int>, Boolean>?
) {
    val mtDateNotProvided = eligibilityOfMidterm == null
    val endSemDateNotProvided = eligibilityOfEndSem == null
    val eligibleMT: Boolean? = eligibilityOfMidterm?.second
    val mtEligibleText = if (mtDateNotProvided) {
        "No Date provided"
    } else if (eligibleMT == true) "Eligible" else "Not Eligible"
    val eligibleEndSem: Boolean? = eligibilityOfEndSem?.second
    val endSemEligibleText = if (endSemDateNotProvided) {
        "No Date provided"
    } else if (eligibleEndSem == true) "Eligible" else "Not Eligible"
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Mid Term: $mtEligibleText",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            if (!mtDateNotProvided) {
                if (eligibleMT == true) {
                    Text(
                        text = "Bunk Lectures: ${eligibilityOfMidterm.first.first}",
                    )
                }
                Text(
                    text = "More Lectures: ${eligibilityOfMidterm!!.first.second}",
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "End Sem: $endSemEligibleText",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            if (!endSemDateNotProvided) {
                if (eligibleMT == true) {
                    Text(
                        text = "Bunk Lectures: ${eligibilityOfEndSem!!.first.first}",
                    )
                }
                Text(
                    text = "More Lectures: ${eligibilityOfEndSem!!.first.second}",
                )
            }
        }
    }
}