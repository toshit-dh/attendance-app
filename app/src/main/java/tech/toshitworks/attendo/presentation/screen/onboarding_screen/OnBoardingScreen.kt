package tech.toshitworks.attendo.presentation.screen.onboarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import tech.toshitworks.attendo.navigation.ScreenRoutes
import tech.toshitworks.attendo.presentation.components.onboarding.OnBoardingPages
import tech.toshitworks.attendo.utils.darkPages
import tech.toshitworks.attendo.utils.description
import tech.toshitworks.attendo.utils.lightPages

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingScreenViewModel,
    navHostController: NavHostController,
    onLoaded: () -> Unit
) {
    LaunchedEffect(Unit) {
        onLoaded()
    }
    val onEvent = viewModel::onEvent
    val inDarkTheme = isSystemInDarkTheme()
    val pages: List<@Composable () -> Unit> = remember(inDarkTheme) {
        if (inDarkTheme) darkPages.map {
            @Composable {
                Box(
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(18.dp))
                        .padding(8.dp),
                ) {
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                        painter = painterResource(id = it.value),
                        contentDescription = it.key
                    )
                }
            }
        } else lightPages.map {
            @Composable {
                Image(
                    painter = painterResource(id = it.value),
                    contentDescription = it.key
                )
            }
        }
    }
    Scaffold {
        OnBoardingPages(
            modifier = Modifier.padding(it),
            count = pages.size,
            pages = pages,
            titles = lightPages.keys.toList(),
            descriptions = description,
        ) {
            onEvent(OnBoardingScreenEvents.OnNextClick)
            navHostController.popBackStack()
            navHostController.navigate(ScreenRoutes.FormScreen.route)
        }
    }
}