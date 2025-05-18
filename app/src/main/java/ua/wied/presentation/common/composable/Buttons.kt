package ua.wied.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier.Companion,
    isEnabled: Boolean = true,
    title: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .alpha(if (isEnabled) 1f else 0.3f),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = WiEDTheme.colors.tintColor,
            containerColor = WiEDTheme.colors.tintColor
        ),
        shape = WiEDTheme.dimen.shape,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        enabled = isEnabled
    ) {
        Text(
            text = title,
            style = WiEDTheme.typography.button1,
            color = Color.Companion.White
        )
    }
}

@Composable
fun PrimaryTextButton(
    title: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
    ) {
        Text(
            text = title,
            style = WiEDTheme.typography.button1,
            color = WiEDTheme.colors.tintColor
        )
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = WiEDTheme.colors.tintColor,
            disabledContentColor = WiEDTheme.colors.tintColor.copy(alpha = ContentAlpha.disabled)
        ),
        shape = WiEDTheme.dimen.shape,
        border = BorderStroke(1.25.dp, WiEDTheme.colors.tintColor),
        contentPadding = PaddingValues(
            horizontal = WiEDTheme.dimen.paddingM,
            vertical = WiEDTheme.dimen.paddingM
        )
    ) {
        Text(
            text = title,
            style = WiEDTheme.typography.button1,
            color = WiEDTheme.colors.tintColor
        )
    }
}

@Composable
fun SquareIconButton(
    modifier: Modifier = Modifier,
    size: Dp = WiEDTheme.dimen.sizeL,
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
        shape = WiEDTheme.dimen.shape,
        contentPadding = PaddingValues(WiEDTheme.dimen.paddingS),
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