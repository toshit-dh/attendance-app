package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ){
        Card(
            Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Mid Term Eligibility",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )
                if (midTermDateProvided)
                    MidTermText(eligibilityOfMidterm!!)
                else
                    Text(
                        text = "No Date Provided",
                    )
            }
        }
        Card(
            Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "End Sem Eligibility",
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

}

@Composable
private fun MidTermText(
    eligibilityOfMidterm: EligibilityData
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Now?: ",
            )
            if (!eligibilityOfMidterm.isEligibleForNow)
                Text(
                    text = "Future?: "
                )
            Text(
                text = "Bunk: "
            )
            Text(
                text = "Attend: "
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = if (eligibilityOfMidterm.isEligibleForNow) Icons.Default.Check else Icons.Default.Close,
                tint = if (eligibilityOfMidterm.isEligibleForNow) Color.Green else Color.Red,
                contentDescription = "eligible?"
            )
            if (!eligibilityOfMidterm.isEligibleForNow)
                Icon(
                    imageVector = if (eligibilityOfMidterm.isEligibleInFuture) Icons.Default.Check else Icons.Default.Close,
                    tint = if (eligibilityOfMidterm.isEligibleInFuture) Color.Green else Color.Red,
                    contentDescription = "eligible?"
                )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${eligibilityOfMidterm.bunkLectures}",
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${eligibilityOfMidterm.moreLectures}",
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun EndSemText(
    eligibilityOfEndSem: EligibilityData
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Now?: ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (!eligibilityOfEndSem.isEligibleForNow)
                Text(
                    text = "Future?: ",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            Text(
                text = "Bunk: ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Attend: ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = if (eligibilityOfEndSem.isEligibleForNow) Icons.Default.Check else Icons.Default.Close,
                tint = if (eligibilityOfEndSem.isEligibleForNow) Color.Green else Color.Red,
                contentDescription = "eligible?"
            )
            if (!eligibilityOfEndSem.isEligibleForNow)
                Icon(
                    imageVector = if (eligibilityOfEndSem.isEligibleInFuture) Icons.Default.Check else Icons.Default.Close,
                    tint = if (eligibilityOfEndSem.isEligibleInFuture) Color.Green else Color.Red,
                    contentDescription = "eligible?"
                )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${eligibilityOfEndSem.bunkLectures}",
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${eligibilityOfEndSem.moreLectures}",
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}