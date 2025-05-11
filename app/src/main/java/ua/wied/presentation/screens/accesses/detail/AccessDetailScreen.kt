package ua.wied.presentation.screens.accesses.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailEvent
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailState
import ua.wied.presentation.screens.main.models.MainEvent

@Composable
fun AccessDetailScreen(
    folder: Folder<Instruction>,
    isEditing: Boolean?,
    isManager: Boolean,
    state: AccessDetailState,
    onEvent: (AccessDetailEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    backToInstructions: () -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

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
                onSuccess = { backToInstructions() },
                onFailure = {

                }
            )
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
                text = state.folder?.title ?: folder.title,
                isEditing = (isEditing != null && isEditing),
                onTextChange = {
                    onEvent(AccessDetailEvent.TitleChanged(it))
                }
            )
        }
    }

    if (showConfirmDialog) {
        SuccessDialog(
            onDismiss = {
                showConfirmDialog = false
                onMainEvent(MainEvent.AccessEditingChanged(null))
            },
            onSuccess = { onEvent(AccessDetailEvent.ChangeData) }
        )
    }
}