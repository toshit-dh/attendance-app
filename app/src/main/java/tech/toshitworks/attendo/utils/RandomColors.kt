package tech.toshitworks.attendo.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun colors(): List<Color> {
    val dark = isSystemInDarkTheme()
    val colors by lazy {
        listOf(
            Color(0xFFE57373),
            Color(0xFF81C784),
            Color(0xFF64B5F6),
            Color(0xFFBA68C8),
            Color(0xFFFF7043),
            Color(0xFF4CAF50),
            Color(0xFF9575CD),
            Color(0xFF29B6F6),
            Color(0xFF9C27B0),
            Color(0xFF8BC34A),
            Color(0xFF673AB7),
            Color(0xFF3F51B5),
            Color(0xFF607D8B)
        )
    }
    return if (!dark) {
        colors.map { darkenColor(it, 0.8f) }
    } else {
        colors.map { darkenColor(it,1.5f) }
    }
}

fun darkenColor(color: Color, factor: Float): Color {
    val red = color.red * factor
    val green = color.green * factor
    val blue = color.blue * factor
    return Color(red.coerceIn(0f, 1f), green.coerceIn(0f, 1f), blue.coerceIn(0f, 1f))
}
