package ua.wied.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ua.wied.R

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    backgroundColor: Color,
    borderColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {

    TextButton(
        modifier = modifier.size(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = iconColor
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(8.dp),
        border = BorderStroke(1.25.dp, borderColor),
        onClick = onClick,
    ) {
        Icon(
            painter = icon,
            tint = iconColor,
            contentDescription = stringResource(R.string.icon)
        )
    }
}