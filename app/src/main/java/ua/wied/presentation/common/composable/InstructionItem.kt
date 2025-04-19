package ua.wied.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun InstructionItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    onClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = { EmptyAction() }
) {
    val clickableModifier = if (onClick != null) {
        modifier.bounceClick(onClick)
    } else {
        modifier
    }

    Row(
        modifier = clickableModifier
            .fillMaxWidth()
            .background(
                colors.secondaryBackground,
                dimen.shape
            )
            .padding(vertical = 16.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomAsyncImage(
            posterUrl = instruction.posterUrl,
            width = 45.dp,
            height = 40.dp,
            shape = dimen.shape
        )

        Text(
            modifier = Modifier
                .padding(start = dimen.paddingS)
                .weight(1f),
            text = instruction.title,
            style = typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(modifier = Modifier.padding(start = dimen.paddingS)) {
            actions()
        }
    }
}

@Composable
private fun EmptyAction() {
    Icon(
        modifier = Modifier.rotate(180f).size(dimen.sizeM),
        imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
        tint = colors.tintColor,
        contentDescription = stringResource(R.string.icon)
    )
}
