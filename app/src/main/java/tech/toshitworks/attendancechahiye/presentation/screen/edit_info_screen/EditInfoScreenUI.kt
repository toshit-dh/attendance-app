package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.presentation.components.edit_screen.SubjectInfo
import tech.toshitworks.attendancechahiye.presentation.components.edit_screen.TimetableInfo
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenViewModel

@Composable
fun EditInfoScreen(
    modifier: Modifier,
    viewModel: EditScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val homeScreenStates by homeScreenViewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
    ) {
            when(homeScreenStates.editInfo){
                1 -> {
                    SubjectInfo(
                        state = state,
                        onEvent = onEvent
                    )
                }
                2 -> {
                    TimetableInfo(
                        state = state,
                        onEvent = onEvent
                    )
                }else -> {

                }
            }
    }
}