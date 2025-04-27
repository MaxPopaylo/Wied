package ua.wied.presentation.screens.instructions.elements.create

import androidx.compose.runtime.Composable
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementEvent
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementState

@Composable
fun CreateElementScreen(
    orderNum: Int,
    state: CreateElementState,
    onEvent: (CreateElementEvent) -> Unit,
    backToInstructionDetail: () -> Unit
) {

}