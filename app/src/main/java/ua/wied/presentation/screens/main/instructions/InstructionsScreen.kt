package ua.wied.presentation.screens.main.instructions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.domain.models.folder.Folder
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.composable.SwipeableItemStatePool
import ua.wied.presentation.screens.main.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.main.instructions.composable.InstructionListItem
import ua.wied.presentation.screens.main.instructions.model.InstructionsEvent.SearchChanged

@Composable
fun InstructionsScreen(
    navController: NavHostController,
    viewModel: InstructionViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val folders = state.folders

    val itemStates = remember(folders) {
        folders.map {
            Folder(
                id = it.id,
                title = it.title,
                orderNum = it.orderNum,
                items = it.items.map { instruction ->
                    SwipeableItemStatePool.getOrCreate(instruction)
                }
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            SwipeableItemStatePool.clear()
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    folders = itemStates,
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