package ua.wied.presentation.screens.instructions.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import ua.wied.R
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.pickers.LargeImagePicker
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.positionAwareImePadding
import ua.wied.presentation.screens.instructions.create.models.CreateInstructionEvent
import ua.wied.presentation.screens.instructions.create.models.CreateInstructionState

@Composable
fun CreateInstructionScreen(
    orderNum: Int,
    folderId: Int,
    state: CreateInstructionState,
    onEvent: (CreateInstructionEvent) -> Unit,
    backToInstructions: (Boolean) -> Unit
) {
    val isButtonEnabled = state.title.isNotEmpty()

    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = { backToInstructions(true) },
                onFailure = {

                }
            )
        }
    }

    Column(
        modifier = Modifier.padding(top = dimen.containerPaddingLarge),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
    ) {
        DetailTextField(
            title = stringResource(R.string.title),
            text = state.title,
            isEditing = true,
            onTextChange = {
                onEvent(CreateInstructionEvent.OnTitleChanged(value = it))
            }
        )

        Text(
            modifier = Modifier.padding(top = dimen.paddingLarge),
            text = stringResource(R.string.photo),
            style = typography.h5.copy(fontSize = 16.sp),
            color = colors.secondaryText
        )
        LargeImagePicker(
            modifier = Modifier.fillMaxWidth(),
            imageUri = state.posterUrl?.toUri(),
            isEditing = true,
            onImageChosen = {
                onEvent(CreateInstructionEvent.OnPosterUrlChanged(it))
            },
            onDeleteImage = {
                onEvent(CreateInstructionEvent.OnPosterUrlChanged(null))
            }
        )

        Spacer(Modifier.weight(1f))

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimen.padding2Xl).positionAwareImePadding(),
            title = stringResource(R.string.create),
            onClick = {
                onEvent(CreateInstructionEvent.Create(orderNum, folderId))
            },
            isEnabled = isButtonEnabled
        )
    }
}