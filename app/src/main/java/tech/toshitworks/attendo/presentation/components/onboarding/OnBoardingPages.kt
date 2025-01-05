package tech.toshitworks.attendo.presentation.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OnBoardingPages(
    modifier: Modifier,
    count: Int,
    pages: List<@Composable () -> Unit>,
    titles: List<String>,
    descriptions: List<String>,
    onSkipOrNextClick: () -> Unit,
) {
    val pagerState = rememberPagerState {
        count
    }
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(10f)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(180.dp)
                                .width(55.dp)
                                .align(Alignment.TopEnd)
                        ){
                            if (it != count - 1)
                                pages[it+1]()
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            pages[it]()
                        }
                        Box(
                            modifier = Modifier
                                .height(180.dp)
                                .width(55.dp)
                                .align(Alignment.BottomStart)
                        ){
                            if (it != 0)
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                ) {
                                    pages[it-1]()
                                }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = titles[it],
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = descriptions[it],
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color, CircleShape)
                            .size(10.dp)
                    )
                }
            }
            ElevatedButton(
                modifier = Modifier
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                onClick = onSkipOrNextClick
            ) {
                Text(
                    text = if (pagerState.currentPage == count - 1) "Get Started" else "Skip"
                )
            }
        }
    }
}