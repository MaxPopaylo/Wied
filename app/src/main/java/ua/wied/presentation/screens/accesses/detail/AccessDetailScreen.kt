package ua.wied.presentation.screens.accesses.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.EmployeesBottomSheet
import ua.wied.presentation.common.composable.ItemList
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.hideBottomSheet
import ua.wied.presentation.common.utils.extensions.showBottomSheet
import ua.wied.presentation.screens.accesses.composable.AccessAvailableForEveryone
import ua.wied.presentation.screens.accesses.composable.AccessListItem
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailEvent
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailState
import ua.wied.presentation.screens.main.models.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessDetailScreen(
    folderId: Int,
    isEditing: Boolean?,
    isManager: Boolean,
    state: AccessDetailState,
    onEvent: (AccessDetailEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    backToInstructions: (Boolean) -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onEvent(AccessDetailEvent.LoadData(folderId))
    }

    LaunchedEffect(isEditing) {
        if (isManager && isEditing != null && !isEditing) {
            if (state.folder?.title?.isNotEmpty() == true) {
                showConfirmDialog = true
            }
        }
    }

    LaunchedEffect(state.updateResult) {
        state.updateResult.collect { result ->
            result?.fold(
                onSuccess = { backToInstructions(true) },
                onFailure = {

                }
            )
        }
    }

    LaunchedEffect(isManager) {
        if (isManager) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                showBottomSheet(coroutineScope, bottomSheetState) {
                    onEvent(AccessDetailEvent.LoadEmployees)
                    showBottomSheet = true
                }
            }))
        }
    }

    ContentBox(
        state = state,
        onRefresh = {}
    ) {
        Column(
            modifier = Modifier.padding(top = dimen.containerPaddingLarge),
            verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
        ) {
            DetailTextField(
                title = stringResource(R.string.title),
                text = state.folder?.title ?: "...",
                isEditing = (isEditing != null && isEditing),
                onTextChange = {
                    onEvent(AccessDetailEvent.TitleChanged(it))
                }
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimen.paddingLarge,
                        bottom = dimen.padding2Xl
                    ),
                text = stringResource(R.string.accesses),
                style = typography.h4
            )

            if (state.folder?.accesses != null && state.folder.accesses.isNotEmpty()) {
                ItemList (
                    items = state.folder.accesses,
                    itemView = { access ->
                        AccessListItem (
                            access = access,
                            onDelete = {
                                onEvent(AccessDetailEvent.AccessToggled(access.id, access.name))
                            }
                        )
                    }
                )
            } else {
                AccessAvailableForEveryone(
                    onAddAccessClick = {
                        showBottomSheet(coroutineScope, bottomSheetState) {
                            onEvent(AccessDetailEvent.LoadEmployees)
                            showBottomSheet = true
                        }
                    }
                )
            }
        }
    }

    if (showConfirmDialog) {
        SuccessDialog(
            onDismiss = {
                showConfirmDialog = false
                onMainEvent(MainEvent.FolderEditingChanged(null))
            },
            onSuccess = { onEvent(AccessDetailEvent.ChangeData) }
        )
    }

    if (showBottomSheet) {
        EmployeesBottomSheet (
            employees = state.employees,
            onClose = {
                hideBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = false
                }
            },
            userChosen = {
                onEvent(AccessDetailEvent.AccessToggled(it.id, it.name))
            }
        )
    }
}