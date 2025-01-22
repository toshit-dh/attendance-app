package tech.toshitworks.attendo.presentation.components.edit_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.presentation.components.dialogs.SelectSubjectDialog
import tech.toshitworks.attendo.presentation.screen.edit_info_screen.EditInfoScreenStates

@Composable
fun TimetableInfo(
    state: EditInfoScreenStates,
) {
    val isUpdatePeriodDialogOpen = remember {
        mutableStateOf(false)
    }
    val periodModel: MutableState<PeriodModel?> = remember {
        mutableStateOf(null)
    }
    val dayModel: MutableState<DayModel?> = remember {
        mutableStateOf(null)
    }
    val index: MutableState<Int?> = remember {
        mutableStateOf(null)
    }
    if (!state.isLoading) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .weight(5f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1.5f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Days",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "arrow forward",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Periods",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                modifier = Modifier.rotate(90f),
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "arrow down",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    LazyColumn {
                        itemsIndexed(state.periodList) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .height(80.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Period ${index + 1}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "${item.startTime} - ${item.endTime}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
                LazyRow(
                    modifier = Modifier.weight(3f)
                ) {
                    itemsIndexed(state.dayList) { indexD, day ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .height(80.dp)
                                    .width(110.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = day.name,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .align(Alignment.CenterHorizontally),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .width(110.dp)
                            ) {
                                itemsIndexed(state.periodList) { indexP, pm ->
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .fillMaxWidth()
                                            .padding(4.dp)
                                            .height(80.dp)
                                            .border(
                                                width = 2.dp,
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (state.listPeriods[(indexD * state.periodList.size) + indexP] == null)
                                            IconButton(
                                                onClick = {
                                                    periodModel.value = pm
                                                    dayModel.value = day
                                                    index.value = (indexD * 7) + indexP
                                                    isUpdatePeriodDialogOpen.value = true
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = "add period",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                )
                                            }
                                        else
                                            Text(
                                                modifier = Modifier
                                                    .clickable {
                                                        periodModel.value = pm
                                                        dayModel.value = day
                                                        index.value = (indexD * 7) + indexP
                                                        isUpdatePeriodDialogOpen.value = true
                                                    },
                                                text = state.listPeriods[(indexD * 7) + indexP]!!.subject.name
                                            )
                                    }
                                }
                            }
                            if (isUpdatePeriodDialogOpen.value)
                                SelectSubjectDialog(
                                    subjectList = state.subjectList,
                                    periodModel = periodModel.value!!,
                                    dayModel = dayModel.value!!,
                                    onDismiss = {
                                        periodModel.value = null
                                        dayModel.value = null
                                        index.value = null
                                        isUpdatePeriodDialogOpen.value = false
                                    }
                                ) {

                                }

                        }
                    }
                }
            }

        }
    }
}


