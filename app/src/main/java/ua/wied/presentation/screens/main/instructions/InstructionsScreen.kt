package ua.wied.presentation.screens.main.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.main.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.main.instructions.composable.InstructionListItem
import ua.wied.presentation.screens.main.instructions.model.InstructionsEvent.SearchChanged

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

        when {
            state.isLoading -> {
                LoadingIndicator(false)
            }
            state.isEmpty -> {
                InstructionEmptyScreen()
            }
            state.isNotInternetConnection -> {
                // TODO: no internet connection
            }
            else -> {
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
}