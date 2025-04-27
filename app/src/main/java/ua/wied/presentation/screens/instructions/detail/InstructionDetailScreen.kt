package ua.wied.presentation.screens.instructions.detail

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
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import ua.wied.R
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.FullScreenImageDialog
import ua.wied.presentation.common.composable.GridVideoItem
import ua.wied.presentation.common.composable.LargeImagePicker
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.composable.MediaGrid
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailEvent
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailState
import ua.wied.presentation.screens.main.models.MainEvent

@Composable
fun InstructionDetailScreen(
    instruction: Instruction,
    isEditing: Boolean?,
    state: InstructionDetailState,
    onEvent: (InstructionDetailEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    navigateToElementDetail: (Element) -> Unit
) {
    var choseImage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(InstructionDetailEvent.LoadData(instruction))
    }

    LaunchedEffect(isEditing) {
        if (isEditing != null && !isEditing) {
            showConfirmDialog = true
        }
    }

    Column(
        modifier = Modifier.padding(top = dimen.containerPaddingLarge),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
    ) {
        DetailTextField(
            title = stringResource(R.string.title),
            text = state.instruction?.title ?: instruction.title,
            isEditing = (isEditing != null && isEditing),
            onTextChange = {
                onEvent(InstructionDetailEvent.TitleChanged(it))
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
            imageUri = state.instruction?.posterUrl?.toUri(),
            isEditing = (isEditing != null && isEditing),
            onViewClick = {
                choseImage = it
                showDialog = true
            },
            onImageChosen = {
                onEvent(InstructionDetailEvent.PosterChanged(it))
            },
            onDeleteImage = {
                onEvent(InstructionDetailEvent.PosterChanged(null))
            }
        )

        if (instruction.elements.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = dimen.paddingLarge),
                text = stringResource(R.string.video),
                style = typography.h5.copy(fontSize = 16.sp),
                color = colors.secondaryText
            )
            MediaGrid(
                urls = instruction.elements.mapNotNull { it.videoUrl },
                gridItem = { url, index ->
                    GridVideoItem(
                        modifier = Modifier.fillMaxWidth(),
                        videoUrl = url,
                        title = instruction.elements[index].title,
                        onViewClick = { navigateToElementDetail(instruction.elements[index]) }
                    )
                }
            )
        }

    }

    if (showDialog) {
        FullScreenImageDialog(
            url = choseImage,
            onDismissRequest = {
                choseImage = ""
                showDialog = false
            }
        )
    }

    if (showConfirmDialog) {
        SuccessDialog(
            onDismiss = {
                showConfirmDialog = false
                onMainEvent(MainEvent.InstructionEditingChanged(null))
            },
            onSuccess = { onEvent(InstructionDetailEvent.ChangeData) }
        )
    }

    if (state.isLoading) {
        LoadingIndicator(false)
    }
}