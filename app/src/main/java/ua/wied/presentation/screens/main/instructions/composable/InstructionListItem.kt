package ua.wied.presentation.screens.main.instructions.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ActionIcon
import ua.wied.presentation.common.composable.InstructionItem
import ua.wied.presentation.common.composable.SwipeableItem
import ua.wied.presentation.common.theme.WiEDTheme.colors

@Composable
fun InstructionListItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    isRevealed: Boolean,
    onExpanded: () -> Unit,
    onCollapsed: () -> Unit,
    onClick: () -> Unit
) {
    SwipeableItem(
        isRevealed = isRevealed,
        onExpanded = onExpanded,
        onCollapsed = onCollapsed,
        actions = {
            ActionIcon(
                backgroundColor = colors.tintColor.copy(alpha = 0.8f),
                icon = painterResource(R.drawable.icon_camcorder),
                tint = Color.White,
                title = stringResource(R.string.video),
                onClick = {}
            )

            ActionIcon(
                backgroundColor = colors.tintColor,
                icon = painterResource(R.drawable.icon_add_person),
                tint = Color.White,
                title = stringResource(R.string.accesses),
                onClick = {}
            )

            ActionIcon(
                backgroundColor = colors.errorColor,
                icon = painterResource(R.drawable.icon_delete),
                tint = Color.White,
                title = stringResource(R.string.delete),
                onClick = {}
            )
        }
    ) {
        InstructionItem(
            modifier = modifier,
            instruction = instruction,
            onClick = onClick,
        )
    }
}