package tech.toshitworks.attendancechahiye.presentation.components.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun FloatingButton(
    onClick: ()->Unit
) {
    IconButton(
        modifier = Modifier
            .offset(y = 10.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.background,
            imageVector = Icons.Default.AddBox,
            contentDescription = "add extra attendance"
        )
    }
}