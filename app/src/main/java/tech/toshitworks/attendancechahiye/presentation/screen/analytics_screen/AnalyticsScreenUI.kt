package tech.toshitworks.attendancechahiye.presentation.screen.analytics_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import tech.toshitworks.attendancechahiye.domain.model.SubjectModel
import tech.toshitworks.attendancechahiye.navigation.ScreenRoutes
import tech.toshitworks.attendancechahiye.presentation.components.analysis.AnalysisForSubject
import tech.toshitworks.attendancechahiye.presentation.screen.home_screen.HomeScreenEvents
import tech.toshitworks.attendancechahiye.utils.colors

@Composable
fun AnalyticsScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: AnalyticsScreenViewModel,
    subjectList: List<SubjectModel>,
    isSubjectSearchOpen: Boolean,
    analysisSubject: SubjectModel?,
    homeScreenEvents: (HomeScreenEvents)->Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val filteredSubjectList = subjectList.filter {
        it.name != "Lunch" && it.name != "No Period"
    }
    val getWeek = viewModel::getWeekDateRange
    if (!state.isLoading) {
        val analysisList = state.analyticsList
        val analysisModel = analysisList.find {
            it.subject == analysisSubject
        }?:analysisList[0]
        val subjectAnalysis = analysisList.drop(1).filter {
            it.subject!!.isAttendanceCounted
        }
        val attendanceList = state.attendanceList
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (!isSubjectSearchOpen)
                AnalysisForSubject(
                    state = state,
                    homeScreenEvents = homeScreenEvents,
                    onEvent = onEvent,
                    analyticsModel = analysisModel,
                    subjectAnalysis = subjectAnalysis,
                    getWeek = getWeek,
                    attendanceList = attendanceList
                ){
                    navController.navigate(ScreenRoutes.EditInfoScreen.route)
                }
            else
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    homeScreenEvents(
                                        HomeScreenEvents.OnSubjectSelectForAnalysis(null)
                                    )
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Overall",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = colors().random(),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    items(filteredSubjectList){
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    homeScreenEvents(
                                        HomeScreenEvents.OnSubjectSelectForAnalysis(it)
                                    )
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                               Text(
                                   text = it.name,
                                   maxLines = 1,
                                   overflow = TextOverflow.Ellipsis,
                                   color = colors().random(),
                                   fontWeight = FontWeight.Bold
                               )
                                Text(
                                    text = it.facultyName,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
        }

    }
}

