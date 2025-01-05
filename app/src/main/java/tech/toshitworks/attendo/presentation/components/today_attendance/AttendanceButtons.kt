package tech.toshitworks.attendo.presentation.components.today_attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttendanceButton(
    type: String,
    deleted: Boolean,
    onClick: ()->Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (deleted) Color.Gray else if (type == "P") Color.Green else Color.Red)
            .border(
                4.dp,
                Color.White,
                RoundedCornerShape(16.dp)
            )

    ) {
        IconButton(
            onClick = onClick,
            enabled = !deleted
            ) {
            Text(
                text = if (type == "P") "P" else "A",
                fontWeight = FontWeight.Bold,
                color = Color.White, fontSize = 20.sp
            )
        }
    }
}