package ua.wied.presentation.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography


@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    shape: Shape = CardDefaults.shape,
    containerColor: Color = Color.White,
    contentColor: Color = Color.Unspecified,
    disabledContainerColor: Color = containerColor,
    disabledContentColor: Color = contentColor,
    elevation: CardElevation = CardDefaults.cardElevation(),
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    hasTitle: Boolean = true,
    onTitleButtonClick: (() -> Unit)? = null,
    titleText: String = "",
    titleStyle: TextStyle = typography.h3,
    titleColor: Color = colors.primaryText,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { onDismissRequest() }
    ) {
        BaseCard(
            modifier = modifier,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
            elevation = elevation,
            enabled = enabled,
            onClick = onClick,
            interactionSource = interactionSource
        ) {
            if (hasTitle) {
                DialogTitle(
                    modifier = Modifier.padding(
                        horizontal = dimen.containerPadding,
                        vertical = dimen.paddingL
                    ),
                    onClick = onTitleButtonClick ?: onDismissRequest,
                    title = titleText,
                    titleStyle = titleStyle,
                    titleColor = titleColor
                )
            }
            content()
        }
    }
}

@Composable
private fun DialogTitle(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    titleStyle: TextStyle = typography.h3,
    titleColor: Color = colors.primaryText
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = titleStyle.copy(color = titleColor)
        )

        Box(
            modifier = Modifier.clickable(
                onClick = { onClick() },
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            )
        ) {
            Icon(
                modifier = Modifier.size(dimen.sizeM),
                imageVector = Icons.Rounded.Close,
                contentDescription = "Close",
            )
        }
    }

    HorizontalDivider(
        thickness = dimen.one,
        color = colors.secondaryText
    )
}