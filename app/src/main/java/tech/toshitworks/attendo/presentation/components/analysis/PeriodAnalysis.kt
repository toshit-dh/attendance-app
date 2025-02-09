package tech.toshitworks.attendo.presentation.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.PeriodAnalysis
import tech.toshitworks.attendo.utils.colors

@Composable
fun PeriodAnalysis(
    periodAnalysis: List<PeriodAnalysis>,
) {
    Column (
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Period Analysis",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(periodAnalysis.size) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    val text = if(periodAnalysis[it].startTime=="empty" && periodAnalysis[it].endTime=="empty")
                        "Extra Period"
                    else "${periodAnalysis[it].startTime} - ${periodAnalysis[it].endTime}"
                    val color = colors().random()
                    Text(
                        text = text,
                        color = color
                    )
                    Text(
                        text = "${periodAnalysis[it].lecturesPresent} / ${periodAnalysis[it].lecturesConducted}",
                        color = color
                    )
                }
            }
        }
    }
}