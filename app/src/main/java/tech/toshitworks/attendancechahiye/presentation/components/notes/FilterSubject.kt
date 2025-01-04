package tech.toshitworks.attendancechahiye.presentation.components.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.Filters
import tech.toshitworks.attendancechahiye.presentation.screen.notes_screen.NotesScreenEvents

@Composable
fun FilterSubject(
    subjects: List<SubjectModel>,
    selectedSubjects: List<Long>,
    onEvent: (NotesScreenEvents) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = "Subject"
            )
            IconButton(
                onClick = {
                    expanded.value = !expanded.value
                }
            ) {
                Icon(
                    imageVector = if (expanded.value) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Select Subject"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ){
                items(subjects.filter {
                    it.id in selectedSubjects
                }, key = {
                    it.id
                }) {
                    Text(
                        text = it.name,
                    )
                }
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                subjects.forEach {
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = it.name,
                                )
                                if (it.id !in selectedSubjects)
                                    IconButton(
                                        onClick = {
                                            onEvent(
                                                NotesScreenEvents.OnChangeFilter(
                                                    Filters.Subject(
                                                        it.id
                                                    )
                                                )
                                            )
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
                                            onEvent(
                                                NotesScreenEvents.OnChangeFilter(
                                                    Filters.Subject(
                                                        it.id
                                                    )
                                                )
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "UnSelect Subject"
                                        )
                                    }
                            }
                        },
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}