package tech.toshitworks.attendo.presentation.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import tech.toshitworks.attendo.domain.model.SubjectModel
import tech.toshitworks.attendo.presentation.screen.form_screen.FormScreenEvents
import tech.toshitworks.attendo.utils.colors

@Composable
fun FormPage3(
    subjectList: List<SubjectModel>,
    onEvent: (event: FormScreenEvents) -> Unit,
    onEditClick: (SubjectModel, Int) -> Unit,
) {
    val name = colors()[0]
    val faculty = colors()[1]
    val attendance = colors()[2]
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        item {
            if (subjectList.isEmpty())
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(4f)
                        ) {
                            Row {
                                Text(
                                    text = "Subject: ",
                                    color = name
                                )

                                Text(
                                    text = "Dummy Subject",
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Row {
                                Text(
                                    text = "Faculty: ",
                                    color = faculty
                                )
                                Text("John Doe")
                            }
                            Row {
                                Text(
                                    text = "Is Attendance Counted? ",
                                    color = attendance
                                )
                                Text(
                                    text = "Yes",
                                    color = Color.Green
                                )
                            }
                        }
                    }
                }
        }
        itemsIndexed(subjectList) { idx, sm ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(4f)
                        ) {
                            Row {
                                Text(
                                    text = "Name: ",
                                    color = name
                                )
                                Text(
                                    text = sm.name,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                )
                            }
                            Row {
                                Text(
                                    text = "Faculty: ",
                                    color = faculty
                                )
                                Text(text = sm.facultyName, overflow = TextOverflow.Ellipsis)
                            }
                            Row {
                                Text(
                                    text = "Is Attendance Counted? ",
                                    color = attendance
                                )
                                Text(
                                    text = if (sm.isAttendanceCounted) "Yes" else "No",
                                    color = if (sm.isAttendanceCounted) Color.Green else Color.Red
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f),
                        ) {
                            IconButton(
                                onClick = {
                                    onEditClick(sm, idx)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "edit subject"
                                )
                            }
                            IconButton(
                                onClick = {
                                    onEvent(FormScreenEvents.OnRemoveSubjectClick(sm))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "delete subject"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

