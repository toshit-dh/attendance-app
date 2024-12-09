package tech.toshitworks.attendancechahiye.presentation.components.analysis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EligibilityAnalysis(
    eligibilityOfMidterm: Pair<Pair<Int, Int>, Boolean>?,
    eligibilityOfEndSem: Pair<Pair<Int, Int>, Boolean>?
) {
    val mtDateNotProvided = eligibilityOfMidterm == null
    val endSemDateNotProvided = eligibilityOfEndSem == null
    val eligibleMT: Boolean? = eligibilityOfMidterm?.second
    val eligibleEndSem: Boolean? = eligibilityOfEndSem?.second
    val midTermText = if (mtDateNotProvided){
        "Mid Term Date not provided"
    }else{
        if (eligibleMT!!){
            val bunkLectures = eligibilityOfMidterm.first.first
            "Eligible for MidTerm. " +
                    if (bunkLectures == 0) "Cant bunk any lectures"
                    else "Can bunk next $bunkLectures lectures"
        }
        else{
            if (eligibilityOfMidterm.first.first > eligibilityOfMidterm.first.second)
                "Not Eligible for MidTerm"
            else
                "Not Eligible for MidTerm. Attend more ${eligibilityOfMidterm.first.first}"
        }
    }
    val endSemText = if (endSemDateNotProvided){
        "End Sem Date not provided"
    }else{
        if (eligibleEndSem!!){
            val bunkLectures = eligibilityOfEndSem.first.first
            "Eligible for End Sem. " +
                    if (bunkLectures == 0) "Cant bunk any lectures"
                    else "Can bunk next $bunkLectures lectures"
        }
        else{
            if (eligibilityOfEndSem.first.first > eligibilityOfEndSem.first.second)
                "Not Eligible for End Sem"
            else
                "Not Eligible for End Sem. Attend more ${eligibilityOfEndSem.first.first}"
        }
    }
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = midTermText,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = endSemText,
            textAlign = TextAlign.Center
        )
    }
}