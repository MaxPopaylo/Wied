package ua.wied.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.dimen

@Composable
fun SquareIconButton(
    modifier: Modifier = Modifier,
    size: Dp = dimen.sizeL,
    icon: ImageVector,
    backgroundColor: Color,
    borderColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {

    TextButton(
        modifier = modifier.size(size).wrapContentWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = iconColor
        ),
        shape = dimen.shape,
        contentPadding = PaddingValues(dimen.paddingS),
        border = BorderStroke(1.25.dp, borderColor),
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            tint = iconColor,
            contentDescription = stringResource(R.string.icon)
        )
    }
}