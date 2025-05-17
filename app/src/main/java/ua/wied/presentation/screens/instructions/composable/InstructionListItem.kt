package ua.wied.presentation.screens.instructions.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ActionIcon
import ua.wied.presentation.common.composable.InstructionItem
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.composable.SwipeToReveal
import ua.wied.presentation.common.theme.WiEDTheme.colors

@Composable
fun InstructionListItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    isManager: Boolean,
    toVideoScreen: (Instruction) -> Unit,
    onDelete: (Int) -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    SwipeToReveal(
        actions = {
            ActionIcon(
                backgroundColor = colors.tintColor.copy(alpha = 0.8f),
                icon = ImageVector.vectorResource(R.drawable.icon_camcorder),
                tint = Color.White,
                title = stringResource(R.string.video),
                onClick = {
                    toVideoScreen(instruction)
                }
            )

            if (isManager) {
                ActionIcon(
                    backgroundColor = colors.tintColor,
                    icon = ImageVector.vectorResource(R.drawable.icon_add_person),
                    tint = Color.White,
                    title = stringResource(R.string.accesses),
                    onClick = {}
                )

                ActionIcon(
                    backgroundColor = colors.errorColor,
                    icon = ImageVector.vectorResource(R.drawable.icon_filled_delete),
                    tint = Color.White,
                    title = stringResource(R.string.delete),
                    onClick = { showConfirmDialog = true }
                )
            }
        },
        onClick = null
    ) {
        InstructionItem(
            modifier = modifier,
            instruction = instruction
        )
    }

    if (showConfirmDialog) {
        SuccessDialog(
            isDelete = true,
            onDismiss = {
                showConfirmDialog = false
            },
            onSuccess = { onDelete(instruction.id) }
        )
    }
}