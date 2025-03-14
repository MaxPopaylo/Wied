package ua.wied.presentation.screens.main.instructions.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.composable.InstructionItem
import ua.wied.presentation.common.theme.WiEDTheme.colors

@Composable
fun InstructionListItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    instructionNum: Int,
    iconOnClick: () -> Unit
) {
    InstructionItem(
        modifier = modifier,
        instruction = instruction,
        instructionNum = instructionNum,
        actions = {
            IconButton(
                icon = painterResource(R.drawable.icon_play),
                backgroundColor = Color.Transparent,
                iconColor = colors.tintColor,
                borderColor = colors.tintColor,
                onClick = iconOnClick
            )
        }
    )
}