package ua.wied.presentation.screens.accesses.create

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.wied.R
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.screens.accesses.create.model.CreateFolderEvent

@Composable
fun CreateFolderDialog(
    viewModel: CreateFolderViewModel = hiltViewModel(),
    orderNum: Int,
    onCreated: () -> Unit,
    onDismiss: () -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val isButtonEnabled = state.title.isNotEmpty()

    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = {
                    viewModel.onEvent(CreateFolderEvent.Created)
                    onCreated()
                },
                onFailure = {

                }
            )
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = colors.primaryBackground,
        confirmButton = {
            PrimaryButton(
                isEnabled = isButtonEnabled,
                title = stringResource(R.string.create),
                onClick = {
                    viewModel.onEvent(CreateFolderEvent.Create(orderNum))
                }
            )
        },
        dismissButton = {
            SecondaryButton (
                title = stringResource(R.string.cancel),
                onClick = {
                    onDismiss()
                }
            )
        },
        title = {
            Text(
                text = stringResource(R.string.create_folder)
            )
        },
        text = {
            DetailTextField(
                title = stringResource(R.string.title),
                text = state.title,
                isEditing = true,
                onTextChange = {
                    viewModel.onEvent(CreateFolderEvent.TitleChanged(it))
                }
            )
        }
    )
}
