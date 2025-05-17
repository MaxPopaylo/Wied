package ua.wied.presentation.screens.accesses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DragAndDropItemList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.accesses.composable.FolderListItem
import ua.wied.presentation.screens.accesses.create.CreateFolderDialog
import ua.wied.presentation.screens.accesses.model.AccessesEvent
import ua.wied.presentation.screens.accesses.model.AccessesState
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.people.composable.EmployeeEmptyScreen

@Composable
fun AccessesScreen(
    state: AccessesState,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit,
    onEvent: (AccessesEvent) -> Unit,
    navigateToCreation: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isManager) {
        if (isManager) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                showCreateDialog = true
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
                onEvent(AccessesEvent.SearchChanged(it))
            }
        )
        ContentBox(
            state = state,
            onRefresh = { onEvent(AccessesEvent.Refresh) },
            emptyScreen = { EmployeeEmptyScreen(navigateToCreation) }
        ) {
            DragAndDropItemList(
                items = state.folders,
                onItemDropped = { folderId, orderNum ->
                    onEvent(AccessesEvent.ChangeOrderNum(folderId, orderNum))
                },
                onItemClick = {
                    navigateToDetail(it.id)
                },
                itemView = { modifier, folder ->
                    FolderListItem (
                        modifier = modifier,
                        folder = folder,
                        isManager = isManager,
                        onDelete = {
                            if (folder.items.size <= 1) {
                                onEvent(AccessesEvent.DeletePressed(it))
                            }
                        }
                    )
                }
            )
        }
    }

    if (showCreateDialog) {
        CreateFolderDialog(
            orderNum = state.folders.last().orderNum + 1,
            onCreated = {
                showCreateDialog = false
                onEvent(AccessesEvent.Refresh)
            },
            onDismiss = {
                showCreateDialog = false
            }
        )
    }
}