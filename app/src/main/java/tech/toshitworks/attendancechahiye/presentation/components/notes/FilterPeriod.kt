package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import tech.toshitworks.attendancechahiye.domain.model.PeriodModel
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.Filters
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenEvents

@Composable
fun FilterPeriod(
    periods: List<PeriodModel>,
    selectedPeriods: List<Long>,
    onEvent: (NotesScreenEvents) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = "Period"
            )
            IconButton(
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Icon(
                    imageVector = if (!expanded.value) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                    contentDescription = "Select Subject"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = periods.find {
                selectedPeriods[0] == it.id
            }!!.id.toString()
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "$text ...",
                textAlign = TextAlign.Center
            )
            Box {
                if (expanded.value)
                    LazyColumn (
                        modifier = Modifier
                            .zIndex(1f)
                    ){
                        items(periods) { pm ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = pm.id.toString(),
                                )
                                if (pm.id !in selectedPeriods)
                                    IconButton(
                                        onClick = {
                                            onEvent(NotesScreenEvents.OnChangeFilter(Filters.Period(pm.id)))
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Select Subject"
                                        )
                                    }
                                else
                                    IconButton(
                                        onClick = {
                                            onEvent(NotesScreenEvents.OnChangeFilter(Filters.Period(pm.id)))
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "UnSelect Subject"
                                        )
                                    }
                            }
                        }
                    }
            }
        }
    }
}