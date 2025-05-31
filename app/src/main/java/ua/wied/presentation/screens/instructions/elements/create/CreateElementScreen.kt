package ua.wied.presentation.screens.instructions.elements.create

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import ua.wied.R
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.pickers.LargeVideoPicker
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.pickers.VideoPickerBottomSheet
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.hideBottomSheet
import ua.wied.presentation.common.utils.extensions.showBottomSheet
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementEvent
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateElementScreen(
    orderNum: Int,
    instructionId: Int,
    state: CreateElementState,
    onEvent: (CreateElementEvent) -> Unit,
    backToInstructionDetail: () -> Unit
) {
    val isButtonEnabled = state.title.isNotEmpty()

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = { backToInstructionDetail() },
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
                onEvent(CreateElementEvent.OnTitleChanged(value = it))
            }
        )

        DetailTextField(
            modifier = Modifier.padding(top = dimen.paddingLarge),
            title = stringResource(R.string.description),
            text = state.info,
            isEditing = true,
            minHeight = 120.dp,
            onTextChange = { value ->
                onEvent(CreateElementEvent.OnInfoChanged(value))
            }
        )

        Text(
            modifier = Modifier.padding(top = dimen.paddingLarge),
            text = stringResource(R.string.photo),
            style = typography.h5.copy(fontSize = 16.sp),
            color = colors.secondaryText
        )
        LargeVideoPicker(
            modifier = Modifier.fillMaxWidth(),
            videoUri = state.videoUrl?.toUri(),
            isEditing = true,
            onChooseVideo = {
                showBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = true
                }
            },
            onDeleteVideo = {
                onEvent(CreateElementEvent.OnVideoUrlChanged(null))
            }
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimen.padding2Xl),
            title = stringResource(R.string.create),
            onClick = {
                onEvent(CreateElementEvent.Create(orderNum, instructionId))
            },
            isEnabled = isButtonEnabled
        )
    }

    if (state.isLoading) {
        LoadingIndicator(false)
    }

    if (showBottomSheet) {
        VideoPickerBottomSheet(
            videoUri = state.videoUrl?.toUri(),
            onClose = {
                hideBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = false
                }
            },
            onVideoChosen = {
                onEvent(CreateElementEvent.OnVideoUrlChanged(it))
            }
        )
    }
}