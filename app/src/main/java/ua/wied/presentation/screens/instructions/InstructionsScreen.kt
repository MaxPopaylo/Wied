package ua.wied.presentation.screens.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.instructions.composable.InstructionListItem
import ua.wied.presentation.screens.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.instructions.model.InstructionsEvent.SearchChanged
import ua.wied.presentation.screens.instructions.model.InstructionsState
import ua.wied.presentation.screens.main.models.MainEvent

@Composable
fun InstructionsScreen(
    state: InstructionsState,
    onEvent: (InstructionsEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    navigateToDetail: (Instruction) -> Unit,
    navigateToCreation: (Int, Int) -> Unit
) {
    LaunchedEffect(state.firstFolderId, state.lastItemOrderNum) {
        if (state.firstFolderId != null && state.lastItemOrderNum != null) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation(state.lastItemOrderNum, state.firstFolderId)
            }))
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)) {
        SearchField(
            text = state.search,
            onSearchValueChange = {
                onEvent(SearchChanged(it))
            }
        )
        ContentBox(
            state = state,
            onRefresh = { onEvent(InstructionsEvent.Refresh) },
            emptyScreen = { InstructionEmptyScreen() }
        ) {
            FolderList (
                folders = state.folders,
                itemView = { instruction ->
                    InstructionListItem(
                        instruction = instruction,
                        onClick = { navigateToDetail(instruction) }
                    )
                }
            )
        }
    }
}