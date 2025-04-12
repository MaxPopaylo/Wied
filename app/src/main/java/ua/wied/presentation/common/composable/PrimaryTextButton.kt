package ua.wied.presentation.common.composable

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

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
            style = typography.button1,
            color = colors.tintColor
        )
    }
}