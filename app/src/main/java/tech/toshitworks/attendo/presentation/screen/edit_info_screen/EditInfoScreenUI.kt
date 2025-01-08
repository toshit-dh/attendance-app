package tech.toshitworks.attendo.presentation.screen.edit_info_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendo.presentation.components.edit_screen.SemesterInfo
import tech.toshitworks.attendo.presentation.components.edit_screen.SubjectInfo
import tech.toshitworks.attendo.presentation.components.edit_screen.TimetableInfo
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator
import tech.toshitworks.attendo.utils.SnackBarEditEvent

@Composable
fun EditInfoScreen(
    modifier: Modifier,
    viewModel: EditScreenViewModel,
    snackBarHostState: SnackbarHostState,
    editInfo: Int
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    LaunchedEffect(true) {
        viewModel.snackBarEvent.collect{
            when(it){
                is SnackBarEditEvent.ShowSnackBarForDataEdited -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        it.message
                    )
                }
                is SnackBarEditEvent.ShowSnackBarForNoChange -> {
                    snackBarHostState.showSnackbar(
                            it.message
                    )
                }
            }
        }
    }
    if (!state.isLoading) {
        Column(
            modifier = modifier
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            when (editInfo) {
                0 -> {
                    Card(
                        modifier = Modifier.weight(5f)
                    ) {
                        SemesterInfo(
                            semesterModel = state.changedSemesterModel!!
                        )
                    }
                }

                1 -> {
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        SubjectInfo(
                            state = state,
                        )
                    }
                }

                2 -> {
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        TimetableInfo(
                            state = state,
                        )
                    }
                }

                else -> {

                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Button(
                modifier = Modifier.weight(0.4f),
                onClick = {
                    when (editInfo) {
                        0 -> {
                            onEvent(EditInfoScreenEvents.OnSaveSemester)
                        }
                        1 -> {

                        }
                        2 -> {

                        }
                        else -> {

                        }
                    }
                },
                enabled = when (editInfo) {
                    0 -> {
                        state.semesterModel != null
                    }
                    1 -> {
                        true
                    }
                    2 -> {
                       true
                    }
                    else -> {
                        false
                    }
                }
            ) {
                Text(
                    text = "Edit Info",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
    else
        LoadingIndicator(modifier)
}