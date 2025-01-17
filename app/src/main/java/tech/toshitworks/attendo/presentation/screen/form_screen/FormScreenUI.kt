package tech.toshitworks.attendo.presentation.screen.form_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.dialogs.AddSubjectDialog
import tech.toshitworks.attendo.presentation.components.forms.FormPage1
import tech.toshitworks.attendo.presentation.components.forms.FormPage2
import tech.toshitworks.attendo.presentation.components.forms.FormPage3
import tech.toshitworks.attendo.presentation.components.forms.FormPage4
import tech.toshitworks.attendo.utils.SnackBarAddEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    viewModel: FormScreenViewModel,
    navController: NavHostController,
    onLoaded: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent1 = viewModel::onFormScreen1Events
    val onEvent234 = viewModel::onFormScreen234Event
    val pages = listOf("page1", "page2", "page3", "page4")
    val pagerState = rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val editSubject = remember {
        mutableStateOf<Pair<SubjectModel?,Int>>(Pair(null,-1))
    }
    val isAddSubjectDialogOpen = remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.event) {
        onLoaded()
        try {
            viewModel.event.collectLatest {
                when (it) {
                    is SnackBarAddEvent.ShowSnackBarForAddingData -> {
                        snackBarHostState.showSnackbar(
                            it.message
                        )
                    }
                    is SnackBarAddEvent.ShowSnackBarForDataAdded -> {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(
                            it.message
                        )
                        delay(500)
                        snackBarHostState.currentSnackbarData?.dismiss()
                        navController.popBackStack()
                        navController.navigate(ScreenRoutes.TimetableScreen.route)
                    }

                    is SnackBarAddEvent.ShowSnackBarForDataNotAdded -> {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        delay(500)
                        snackBarHostState.showSnackbar(
                            it.message
                        )
                        delay(500)
                        snackBarHostState.currentSnackbarData?.dismiss()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("FormScreen Event Collection", "FormScreen: ${e.message}")
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    val text = when (pagerState.currentPage) {
                        0 -> {
                            "Add Semester Info"
                        }

                        1 -> {
                            "Add Working Days"
                        }

                        2 -> {
                            "Add Subjects"
                        }

                        3 -> {
                            "Add Periods Info"
                        }

                        else -> {
                            "Attendo"
                        }
                    }
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
                actions = {
                    if (pagerState.currentPage == 2) {
                        IconButton(
                            onClick = {
                                isAddSubjectDialogOpen.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "add subject"
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .weight(9f)
                    .padding(8.dp)
                    .fillMaxWidth(),
            ) {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0 -> FormPage1(state.semesterModel, onEvent1)
                        1 -> FormPage2(state.dayList, onEvent234)
                        2 -> FormPage3(state.subjectList, onEvent234){sm,idx->
                            editSubject.value = Pair(sm,idx)
                            isAddSubjectDialogOpen.value = true
                        }
                        3 -> FormPage4(state.periodList, onEvent234)
                    }
                }
            }
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .weight(1f),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text(
                            "Previous",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
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
                        enabled = when (pagerState.currentPage) {
                            0 -> {
                                state.semesterModel != null &&
                                state.semesterModel?.startDate != ""
                            }
                            1 -> state.semesterModel != null && state.dayList.isNotEmpty() && state.dayList.size in 1..7
                            2 -> state.subjectList.isNotEmpty()
                            else -> false
                        },
                        colors = ButtonDefaults.buttonColors().copy(
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.5f
                            )
                        )
                    ) {
                        Text(
                            "Next",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                if (pagerState.currentPage == pages.size - 1) {
                    Button(
                        onClick = {
                            onEvent234(FormScreenEvents.AddInRoom)
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                            .weight(1f),
                        shape = MaterialTheme.shapes.medium,
                        enabled = state.periodList.isNotEmpty()
                    ) {
                        Text(
                            "Done",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
            if (isAddSubjectDialogOpen.value) {
                AddSubjectDialog(
                    onDismiss = { isAddSubjectDialogOpen.value = false },
                    onAddSubject = {sm->
                        onEvent234(FormScreenEvents.OnAddSubjectClick(sm))
                        isAddSubjectDialogOpen.value = false
                    },
                    subjectModel = editSubject.value.first,
                ){sm->
                    onEvent234(FormScreenEvents.OnEditSubjectClick(sm,editSubject.value.second))
                    editSubject.value = Pair(null,-1)
                    isAddSubjectDialogOpen.value = false
                }
            }
        }
    }
}
