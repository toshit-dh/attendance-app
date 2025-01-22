package tech.toshitworks.attendo.presentation.screen.timetable_screen

import android.util.Log
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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import tech.toshitworks.attendo.domain.model.DayModel
import tech.toshitworks.attendo.domain.model.PeriodModel
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.dialogs.SelectSubjectDialog
import tech.toshitworks.attendo.utils.SnackBarAddEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(
    viewModel: TimeTableViewModel,
    navController: NavHostController,
    onLoaded: () -> Unit
) {
    LaunchedEffect(Unit) {
        onLoaded()
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
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
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(Unit) {
        try {
            viewModel.event.collect { event ->
                when (event) {
                    is SnackBarAddEvent.ShowSnackBarForAddingData -> {
                        try {
                            snackBarHostState.showSnackbar(event.message)
                        }catch (e: Exception){
                            Log.e("SnackBarEvent", "Error handling ShowSnackBarForAddingData: ${e.message}", e)
                        }
                    }
                    is SnackBarAddEvent.ShowSnackBarForDataAdded -> {
                        try {
                            snackBarHostState.currentSnackbarData?.dismiss()
                            delay(500)
                            snackBarHostState.showSnackbar(event.message)
                            delay(500)
                            snackBarHostState.currentSnackbarData?.dismiss()
                            navController.popBackStack()
                            navController.navigate(ScreenRoutes.HomeScreen.route)
                        } catch (e: Exception) {
                            Log.e("SnackBarEvent", "Error handling ShowSnackBarForDataAdded: ${e.message}", e)
                        }
                    }

                    is SnackBarAddEvent.ShowSnackBarForDataNotAdded -> {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        delay(500)
                        snackBarHostState.showSnackbar(event.message)
                        delay(500)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("EventCollection", "Error collecting events: ${e.message}", e)
        }
    }

    if (!state.isLoading) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                TopAppBar(
                    title = {
                        val text =
                            "${state.filled} / ${state.total} Filled ${if (state.filled == state.total) "Ready to go" else ""}"
                        Text(
                            text = text,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            }
        ) { pv ->
            Column(
                modifier = Modifier
                    .padding(pv),
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
                        modifier = Modifier.weight(1f)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                                    contentDescription = "arrow forward",
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
                                                        index.value = (indexD * state.periodList.size) + indexP
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
                                                            index.value = (indexD * state.periodList.size) + indexP
                                                            isUpdatePeriodDialogOpen.value = true
                                                        },
                                                    text = state.listPeriods[(indexD * state.periodList.size) + indexP]!!.subject.name,
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
                                        onEvent(
                                            TimeTableScreenEvents.OnAddPeriod(
                                                it,
                                                index.value!!
                                            )
                                        )
                                    }

                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    ElevatedButton(
                        onClick = {
                            onEvent(TimeTableScreenEvents.OnNextClick)
                        },
                        enabled = state.listPeriods.all {
                            it != null
                        }
                    ) {
                        Text(
                            text = "Save & Next"
                        )
                    }
                }
            }
        }
    }
}

