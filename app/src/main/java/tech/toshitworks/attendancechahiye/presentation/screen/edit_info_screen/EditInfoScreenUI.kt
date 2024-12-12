package tech.toshitworks.attendancechahiye.presentation.screen.edit_info_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.presentation.components.forms.FormPage1

@Composable
fun EditInfoScreen(
    modifier: Modifier,
    viewModel: EditScreenViewModel
) {
    Column(
        modifier = modifier
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val onEvent = viewModel::onEvent
        FormPage1(state.semesterModel, onEvent)
    }
}