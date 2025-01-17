package tech.toshitworks.attendo.presentation.screen.delete_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.indicators.LoadingIndicator
import tech.toshitworks.attendo.utils.SnackBarDeleteEvent
import tech.toshitworks.attendo.utils.updateShortcut

@Composable
fun DeleteScreen(
    modifier: Modifier,
    viewModel: DeleteScreenViewModel,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    beforeNavController: NavHostController
) {
    val context = LocalContext.current
    val isDeleteChecked = remember {
        mutableStateOf(false)
    }
    val deletingData = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collectLatest {
            when (it) {
                is SnackBarDeleteEvent.ShowSnackBarForDataDeleted -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    delay(500)
                    snackBarHostState.showSnackbar(it.message)
                    deletingData.value = false
                    navController.popBackStack()
                    updateShortcut(context, false)
                    beforeNavController.navigate(ScreenRoutes.FormScreen.route){
                        popUpTo(ScreenRoutes.HomeScreen.route){
                            inclusive = true
                        }
                    }
                }

                is SnackBarDeleteEvent.ShowSnackBarForDataNotDeleted -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    delay(500)
                    snackBarHostState.showSnackbar(it.message)

                }

                is SnackBarDeleteEvent.ShowSnackBarForDeletingData -> {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    delay(500)
                    snackBarHostState.showSnackbar(it.message)
                }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Delete the semester data to add new semester",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                ElevatedButton(
                    onClick = {
                        navController.navigate(ScreenRoutes.ExportScreen.route)
                    },
                    enabled = !deletingData.value
                ) {
                    Text(
                        text = "Export the data if necessary",
                        textDecoration = TextDecoration.Underline
                    )
                }
                Text(
                    text = "This action will delete all your semester data.\n" +
                            "It will not delete the settings.",
                    textAlign = TextAlign.Center
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Checkbox(
                        checked = isDeleteChecked.value,
                        onCheckedChange = {
                            isDeleteChecked.value = it
                        },
                        enabled = !deletingData.value
                    )
                    Text(
                        text = "Are you sure you want to delete the data?"
                    )
                }
                ElevatedButton(
                    onClick = {
                        deletingData.value = true
                        viewModel.onDelete()
                    },
                    enabled = isDeleteChecked.value,
                ) {
                    Text(
                        text = "Delete The Data"
                    )
                }
                if (deletingData.value)
                    LoadingIndicator(
                        modifier = Modifier
                            .weight(1f),
                        text = "Deleting ..."
                    )
            }
        }
    }
}
