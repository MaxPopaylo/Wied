package ua.wied.presentation.screens.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DragAndDropFolderList
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
    isManager: Boolean,
    savedStateHandle: SavedStateHandle,
    onEvent: (InstructionsEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    navigateToVideoScreen: (Instruction) -> Unit,
    navigateToDetail: (Instruction) -> Unit,
    navigateToCreation: (Int, Int) -> Unit,
    navigateToAccess: (Int) -> Unit
) {
    val shouldRefresh = savedStateHandle
        .getStateFlow("shouldRefresh", false)
        .collectAsState()

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh.value) {
            onEvent(InstructionsEvent.Refresh)
            savedStateHandle["shouldRefresh"] = false
        }
    }

    LaunchedEffect(state.firstFolderId, state.lastItemOrderNum) {
        if (isManager && state.firstFolderId != null && state.lastItemOrderNum != null) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation(state.lastItemOrderNum, state.firstFolderId)
            }))
        }
    }

    Column(
        modifier = Modifier.padding(bottom = dimen.paddingS),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        SearchField(
            text = state.search,
            onSearchValueChange = {
                onEvent(SearchChanged(it))
            }
        )
        ContentBox(
            state = state,
            onRefresh = { onEvent(InstructionsEvent.Refresh) },
            emptyScreen = {
                InstructionEmptyScreen(
                    isManager = isManager,
                    onCreationClick = {
                        if (state.firstFolderId != null && state.lastItemOrderNum != null) {
                            navigateToCreation(state.lastItemOrderNum, state.firstFolderId)
                        }
                    }
                )
            }
        ) {
            DragAndDropFolderList (
                folders = state.folders,
                onItemDropped = { instructionId, folderId, orderNum ->
                    onEvent(InstructionsEvent.ChangeOrderNum(instructionId, folderId, orderNum))
                },
                onItemClick = { navigateToDetail(it) } ,
                itemView = { modifier, instruction ->
                    InstructionListItem(
                        modifier = modifier,
                        instruction = instruction,
                        isManager = isManager,
                        toVideoScreen = navigateToVideoScreen,
                        onDelete = {
                            onEvent(InstructionsEvent.DeletePressed(it))
                        },
                        onAccess = {
                            navigateToAccess(it)
                        }
                    )
                }
            )
        }
    }
}