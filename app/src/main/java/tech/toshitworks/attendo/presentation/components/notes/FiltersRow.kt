package tech.toshitworks.attendo.presentation.components.notes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.presentation.screen.notes_screen.Filters
import tech.toshitworks.attendo.presentation.screen.notes_screen.NotesScreenEvents
import tech.toshitworks.attendo.presentation.screen.notes_screen.NotesScreenStates

@Composable
fun FiltersRow(
    modifier: Modifier,
    states: NotesScreenStates,
    onEvent: (NotesScreenEvents) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Card {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterSubject(
                        subjects = states.subjects,
                        selectedSubjects = states.subjectFilter,
                        onEvent = onEvent
                    )
                }
            }
        }
        item {
            Card {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterDay(
                        days = states.days,
                        selectedDays = states.dayFilter,
                        onEvent = onEvent
                    )
                }
            }
        }
        item {
            Card {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterPeriod(
                        periods = states.periods,
                        selectedPeriods = states.periodFilter,
                        onEvent = onEvent
                    )
                }
            }
        }
        item {
            Card {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterAttend(
                        byAttendance = states.attend,
                        onEvent = onEvent
                    )
                }
            }
        }
        item {
            Card {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterFromDate(
                        startDate = states.datesFilter.first,
                        minStartDate = states.startDate
                    ){
                        onEvent(NotesScreenEvents.OnChangeFilter(Filters.Date(Pair(it,states.datesFilter.second))))
                    }
                }
            }
        }
        item {
            Card {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterToDate(
                        startDate = states.datesFilter.second,
                        minStartDate = states.startDate
                    ){
                        onEvent(NotesScreenEvents.OnChangeFilter(Filters.Date(Pair(states.datesFilter.first,it))))
                    }
                }
            }
        }
    }
}