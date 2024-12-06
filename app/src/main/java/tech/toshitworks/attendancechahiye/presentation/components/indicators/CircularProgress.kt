import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun CircularProgress(
    modifier: Modifier,
    percentage: Float
) {
    val progress = percentage.coerceIn(0f, 100f)
    val color = when {
        progress < 50 -> Color.Red
        progress in 50f..75f -> Color.Yellow
        else -> Color.Green
    }
    val text = String.format(Locale.US,"%.2f",percentage)
    val textStyle = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center
    )
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text, style = textStyle)

    val brush = Brush.linearGradient(
        colors = listOf(MaterialTheme.colorScheme.onSurface,MaterialTheme.colorScheme.onSurface)
    )
        Canvas(
            modifier = modifier
                .fillMaxSize()
        ) {
            val arcSize = 180f
            drawArc(
                topLeft = Offset(
                    x = (size.width-arcSize)/2,
                    y = (size.height-arcSize)/2
                ),
                color = Color.Gray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size.copy(arcSize, arcSize),
                style = Stroke(width = 20f)
            )

            // Draw the progress arc
            drawArc(
                topLeft = Offset(
                    x = (size.width-arcSize)/2,
                    y = (size.height-arcSize)/2
                ),
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * (progress / 100f),
                useCenter = false,
                size = size.copy(arcSize, arcSize),
                style = Stroke(width = 20f)
            )

            val x = (size.width - textLayoutResult.size.width )/ 2
            val y = (size.height - textLayoutResult.size.height )/ 2

            // Draw the text at the center
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x, y),
                brush = brush
            )
        }

}


