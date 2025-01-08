package tech.toshitworks.attendo.presentation.components.indicators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spr.jetpack_loading.components.indicators.BallTrianglePathIndicator

@Composable
fun LoadingIndicator(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BallTrianglePathIndicator(
            color = MaterialTheme.colorScheme.primary,
            movingBalls = 5
        )
        Spacer(modifier = Modifier
            .height(40.dp)
        )
        Text(
            text = "Loading ...",
            style = MaterialTheme.typography.titleLarge
        )
    }
}