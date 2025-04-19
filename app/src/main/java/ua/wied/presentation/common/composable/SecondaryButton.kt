package ua.wied.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

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
            contentColor = colors.tintColor,
            disabledContentColor = colors.tintColor.copy(alpha = ContentAlpha.disabled)
        ),
        shape = dimen.shape,
        border = BorderStroke(1.25.dp, colors.tintColor),
        contentPadding = PaddingValues(horizontal = dimen.paddingM, vertical = dimen.paddingM)
    ) {
        Text(
            text = title,
            style = typography.button1,
            color = colors.tintColor
        )
    }
}
