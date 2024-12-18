package tech.toshitworks.attendancechahiye.presentation.screen.edit_attendance_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun EditAttendanceScreen(
    modifier: Modifier,
    viewModel: EditAttendanceScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    var isDropDownMenuVisible by remember {
        mutableStateOf(false)
    }
    if (!state.isLoading)
        Column(
            modifier = modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card {
                    Text(
                        text = state.date ?: "Date?"
                    )
                }
                Card {
                    Row {
                        Text(
                            text = state.selectedSubject?.name ?: "Subject?"
                        )
                        DropdownMenu(
                            expanded = isDropDownMenuVisible,
                            onDismissRequest = {
                                isDropDownMenuVisible = false
                            }
                        ) {
                            state.subjects.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.name
                                        )
                                    },
                                    onClick = {
                                        onEvent(EditAttendanceScreenEvents.OnSubjectSelected(it))
                                        isDropDownMenuVisible = false
                                    }
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                isDropDownMenuVisible = !isDropDownMenuVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (state.selectedSubject == null) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                                contentDescription = "select subject"
                            )
                        }
                    }
                }

            }
        }
}