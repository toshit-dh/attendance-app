package tech.toshitworks.attendancechahiye.presentation.screen.home_screen

import CircularProgress
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import tech.toshitworks.attendancechahiye.domain.model.DayModel
import tech.toshitworks.attendancechahiye.presentation.components.bars.BottomBar
import tech.toshitworks.attendancechahiye.presentation.components.bars.TopBar
import tech.toshitworks.attendancechahiye.presentation.components.dialogs.ChangeSubjectDialog
import tech.toshitworks.attendancechahiye.presentation.components.home.TimetableForDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun HomeScreen(
    drawerState: DrawerState,
    viewModel: HomeScreenViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = today.format(formatter)
    val day = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val isSubjectChangedDialogOpen = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopBar(drawerState = drawerState)
        },
        bottomBar = {
            BottomBar()
        },
    ) {
        if(!state.isLoading)
            Column(
                modifier = Modifier.padding(it)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(12.dp),
                ) {
                    Text(
                        text = "Overall Attendance",
                        modifier = Modifier.padding(8.dp, 3.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${state.attendanceStats.totalPresent} / ${state.attendanceStats.totalLectures}",
                            fontSize = 20.sp
                        )
                        CircularProgress(
                            modifier = Modifier.weight(1f),
                            percentage = state.attendanceStats.attendancePercentage.toFloat()
                        )
                    }
                }
                if (state.dayList.contains(day)) {
                    TimetableForDay(
                        state = state,
                        onEvent = onEvent,
                        onEditIconClick = {
                            isSubjectChangedDialogOpen.value = true
                        },
                        date = date,
                        day = day
                    )
                    if (isSubjectChangedDialogOpen.value)
                        ChangeSubjectDialog(
                            subjects = state.subjectList,
                            onDismiss = {
                                isSubjectChangedDialogOpen.value = false
                            }
                        ) { a, b ->

                        }
                }
        }
    }
}

