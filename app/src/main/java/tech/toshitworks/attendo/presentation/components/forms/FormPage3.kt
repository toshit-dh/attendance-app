package tech.toshitworks.attendo.presentation.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.attendo.presentation.components.dialogs.AddSubjectDialog
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenEvents
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenStates

@Composable
fun FormPage3(
    state: FormScreenStates,
    onEvent: (event: FormScreenEvents) -> Unit
) {
    val isAddSubjectDialogOpen = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Add Subjects",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
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
        LazyColumn {
            items(state.subjectList) { sm ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier.padding(16.dp)
                                .weight(4f)
                        ) {
                            Text(text = sm.name, textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis)
                            Row {
                                Text("Faculty Name: ")
                                Text(text = sm.facultyName, overflow = TextOverflow.Ellipsis)
                            }
                            Row {
                                Text("Is Attendance Counted? ")
                                Text(text = if (sm.isAttendanceCounted )"Yes" else "No")
                            }
                        }
                        IconButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                               onEvent(FormScreenEvents.OnRemoveSubjectClick(sm))
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Delete,
                                contentDescription = "delete subject")
                        }
                    }
                }
            }
        }
        if (isAddSubjectDialogOpen.value) {
            AddSubjectDialog(
                onDismiss = { isAddSubjectDialogOpen.value = false },
                onAddSubject = {
                    onEvent(FormScreenEvents.OnAddSubjectClick(it))
                    isAddSubjectDialogOpen.value = false
                }
            )
        }
    }
}

