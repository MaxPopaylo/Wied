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
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.FullScreenImageDialog
import ua.wied.presentation.common.composable.GridVideoItem
import ua.wied.presentation.common.composable.LargeImagePicker
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
    isManager: Boolean,
    state: InstructionDetailState,
    onEvent: (InstructionDetailEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit,
    navigateToElementDetail: (Element) -> Unit,
    navigateToCreation: (Int, Int) -> Unit,
    backToInstructions: () -> Unit
) {
    var choseImage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEvent(InstructionDetailEvent.LoadData(instruction.id))
    }

    LaunchedEffect(isEditing) {
        if (isEditing != null && !isEditing) {
            showConfirmDialog = true
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

    LaunchedEffect(state.lastItemOrderNum) {
        if (isManager &&  state.lastItemOrderNum != null) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation(state.lastItemOrderNum, instruction.id)
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

            if (state.instruction?.elements?.isNotEmpty() == true) {
                Text(
                    modifier = Modifier.padding(top = dimen.paddingLarge),
                    text = stringResource(R.string.instruction_items),
                    style = typography.h5.copy(fontSize = 16.sp),
                    color = colors.secondaryText
                )
                MediaGrid(
                    urls = state.instruction.elements.mapNotNull { it.videoUrl },
                    gridItem = { url, index ->
                        GridVideoItem(
                            modifier = Modifier.fillMaxWidth(),
                            videoUrl = url,
                            title = state.instruction.elements[index].title,
                            onViewClick = { navigateToElementDetail(state.instruction.elements[index]) }
                        )
                    }
                )
            }

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
}