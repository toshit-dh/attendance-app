package tech.toshitworks.attendancechahiye.presentation.screen.form_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.components.forms.FormPage1
import tech.toshitworks.attendancechahiye.presentation.components.forms.FormPage2
import tech.toshitworks.attendancechahiye.presentation.components.forms.FormPage3
import tech.toshitworks.attendancechahiye.presentation.components.forms.FormPage4
import tech.toshitworks.attendancechahiye.utils.SnackBarEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    viewModel: FormScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val pages = listOf("page1", "page2", "page3", "page4")
    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(true) {
        viewModel.event.collect {
            when (it) {
                is SnackBarEvent.ShowSnackBarForAddingData -> {
                    snackBarHostState.showSnackbar(
                        it.message
                    )
                }

                is SnackBarEvent.ShowSnackBarForDataAdded -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    delay(500)
                    snackBarHostState.showSnackbar(
                        it.message
                    )
                    delay(500)
                    snackBarHostState.currentSnackbarData?.dismiss()
                    navController.popBackStack()
                    navController.navigate(ScreenRoutes.TimetableScreen.route)
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Attendance Chahiye",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(800.dp),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    when (page) {
                        0 -> FormPage1(state, onEvent)
                        1 -> FormPage2(state, onEvent)
                        2 -> FormPage3(state, onEvent)
                        3 -> FormPage4(state, onEvent)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (pagerState.currentPage > 0) {
                        Button(
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .height(48.dp)
                                .weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(
                                "Previous",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                    if (pagerState.currentPage < pages.size - 1) {
                        Button(
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .height(48.dp)
                                .weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            enabled = when (pagerState.currentPage) {
                                0 -> state.semesterModel != null
                                1 -> state.semesterModel != null && state.dayList.isNotEmpty() && state.dayList.size in 1..7
                                2 -> state.subjectList.isNotEmpty()
                                else -> false
                            }
                        ) {
                            Text(
                                "Next",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                    if (pagerState.currentPage == pages.size - 1) {
                        Button(
                            onClick = {
                                onEvent(FormScreenEvents.AddInRoom)
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .height(48.dp)
                                .weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            enabled = state.periodList.isNotEmpty()
                        ) {
                            Text(
                                "Done",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                }
            }
        }
    }
}