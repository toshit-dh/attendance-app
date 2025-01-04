package tech.toshitworks.attendancechahiye.presentation.screen.export_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import tech.toshitworks.attendancechahiye.utils.SnackBarWorkerEvent

@Composable
fun ExportScreen(
    modifier: Modifier,
    snackBarHostState: SnackbarHostState,
    viewModel: ExportScreenViewModel,
) {
    val onExport = viewModel::onExport
    val list = listOf(
        "Subjects",
        "Timetable",
        "Attendance",
        "Events",
    )
    val selectedItems: MutableState<List<String>> = remember {
        mutableStateOf(emptyList())
    }
    LaunchedEffect(true) {
        viewModel.snackBarEvent.collect {
            when(it){
                is SnackBarWorkerEvent.ShowSnackBarForCSVWorker -> {
                    snackBarHostState.showSnackbar(
                        it.message
                    )
                    delay(500)
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            }
        }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Text(
                text = "Select tables to be exported",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        items(list) { s ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = s
                    )
                    Checkbox(
                        checked = selectedItems.value.contains(s),
                        onCheckedChange = {
                            if (it) {
                                selectedItems.value += s
                            } else {
                                if (s == "Attendance") selectedItems.value -= "Notes"
                                selectedItems.value -= s
                            }
                        }
                    )
                }
                if (s == "Attendance" && selectedItems.value.contains(s))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Spacer(modifier = Modifier
                            .weight(0.2f)
                            .fillMaxWidth()
                        )
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                            , verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = "Notes"
                            )
                            Checkbox(
                                checked = selectedItems.value.contains("Notes"),
                                onCheckedChange = {
                                    if (it) {
                                        selectedItems.value += "Notes"
                                    } else {
                                        selectedItems.value -= "Notes"
                                    }
                                }
                            )
                        }
                    }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        onExport(selectedItems.value)
                    },
                    enabled = selectedItems.value.isNotEmpty()
                ) {
                    Text(
                        text = "Export ${selectedItems.value}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
