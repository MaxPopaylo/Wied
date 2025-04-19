package ua.wied.presentation.screens.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.instructions.composable.InstructionListItem
import ua.wied.presentation.screens.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.instructions.model.InstructionsEvent.SearchChanged

@Composable
fun InstructionsScreen(
    navController: NavHostController,
    viewModel: InstructionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        SearchField(
            text = state.search,
            onSearchValueChange = {
                viewModel.onEvent(SearchChanged(it))
            }
        )

        ContentBox(
            state = state,
            onRefresh = { viewModel.onEvent(InstructionsEvent.Refresh) },
            emptyScreen = { InstructionEmptyScreen() }
        ) {
            FolderList (
                folders = state.folders,
                itemView = { instruction ->
                    InstructionListItem(
                        instruction = instruction,
                        onClick = {}
                    )
                }
            )
        }
    }
}