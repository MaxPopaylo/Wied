package ua.wied.presentation.screens.main.instructions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.screens.main.instructions.composable.InstructionListItem

@Composable
fun InstructionsScreen(
    navController: NavHostController,
    viewModel: InstructionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column {
        when {
            state.isLoading -> {
                LoadingIndicator(false)
            }
            state.isEmpty -> {
                // TODO: empty screen
            }
            state.isNotInternetConnection -> {
                // TODO: no internet connection
            }
            else -> {
                FolderList(
                    folders = state.folders,
                    itemView = { data ->
                        InstructionListItem(
                            isRevealed = data.isRevealed,
                            instruction = data.item,
                            onExpanded = {
                                data.isRevealed = true
                            },
                            onCollapsed = {
                                data.isRevealed = false
                            },
                            onClick = {}
                        )
                    }
                )
            }
        }
    }
}