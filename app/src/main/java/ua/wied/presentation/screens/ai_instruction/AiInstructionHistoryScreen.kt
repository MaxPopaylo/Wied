package ua.wied.presentation.screens.ai_instruction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.ItemList
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.ai_instruction.composable.AiInstructionEmptyScreen
import ua.wied.presentation.screens.ai_instruction.composable.AiInstructionListItem
import ua.wied.presentation.screens.ai_instruction.model.AiInstructionHistoryEvent
import ua.wied.presentation.screens.ai_instruction.model.AiInstructionHistoryState
import ua.wied.presentation.screens.main.models.MainEvent

@Composable
fun AiInstructionHistoryScreen(
    state: AiInstructionHistoryState,
    isManager: Boolean,
    savedStateHandle: SavedStateHandle,
    onEvent: (AiInstructionHistoryEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    navigateToCreation: () -> Unit
) {
    val shouldRefresh = savedStateHandle
        .getStateFlow("shouldRefresh", false)
        .collectAsState()

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh.value) {
            onEvent(AiInstructionHistoryEvent.Refresh)
            savedStateHandle["shouldRefresh"] = false
        }
    }

    LaunchedEffect(isManager) {
        if (isManager) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation()
            }))
        }
    }

    Column(
        modifier = Modifier.padding(bottom = dimen.paddingS),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        ContentBox(
            state = state,
            onRefresh = { onEvent(AiInstructionHistoryEvent.Refresh) },
            emptyScreen = {
                AiInstructionEmptyScreen(
                    isManager = isManager,
                    onCreationClick = {
                        navigateToCreation()
                    }
                )
            }
        ) {
            ItemList (
                items = state.history,
                reverseLayout = true,
                itemView = { response ->
                    AiInstructionListItem(response = response)
                }
            )
        }
    }
}