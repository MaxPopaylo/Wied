package ua.wied.presentation.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(42.dp).alpha(if (isEnabled) 1f else 0.3f),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = colors.tintColor,
            containerColor = colors.tintColor
        ),
        shape = dimen.shape,
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        enabled = isEnabled
    ) {
        Text(
            text = title,
            style = typography.w700.copy(
                color = colors.tertiaryText,
                fontSize = 16.sp
            )
        )
    }
}