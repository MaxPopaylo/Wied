package ua.wied.presentation.screens.instructions.elements

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.repository.VideoPlayerEvent
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.FullScreenImageDialog
import ua.wied.presentation.common.composable.FullScreenVideoDialog
import ua.wied.presentation.common.composable.LargeVideoPicker
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.composable.VideoPickerBottomSheet
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.hideBottomSheet
import ua.wied.presentation.common.utils.extensions.showBottomSheet
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailEvent
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailState
import ua.wied.presentation.screens.instructions.video.model.VideoEvent
import ua.wied.presentation.screens.main.models.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementDetailScreen(
    element: Element,
    isEditing: Boolean?,
    state: ElementDetailState,
    onEvent: (ElementDetailEvent) -> Unit,
    onPlayerEvent: (VideoPlayerEvent) -> Unit,
    onMainEvent: (MainEvent) -> Unit
) {
    var choseImage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var showConfirmDialog by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
       onEvent(ElementDetailEvent.LoadDate(element))
    }

    LaunchedEffect(isEditing) {
        if (isEditing != null && !isEditing) {
            showConfirmDialog = true
        }
    }

    Column(
        modifier = Modifier
            .padding(top = dimen.containerPaddingLarge)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
    ) {
        DetailTextField(
            title = stringResource(R.string.title),
            text = state.element?.title ?: element.title,
            isEditing = (isEditing != null && isEditing),
            onTextChange = { value ->
                onEvent(ElementDetailEvent.TitleChanged(value))
            }
        )

        (state.element?.info ?: element.info) ?.let {
            DetailTextField(
                title = stringResource(R.string.title),
                text = it,
                isEditing = (isEditing != null && isEditing),
                minHeight = 120.dp,
                onTextChange = { value ->
                    onEvent(ElementDetailEvent.InfoChanged(value))
                }
            )
        }

        Text(
            modifier = Modifier.padding(top = dimen.paddingLarge),
            text = stringResource(R.string.video),
            style = typography.h5.copy(fontSize = 16.sp),
            color = colors.secondaryText
        )
        LargeVideoPicker(
            modifier = Modifier.fillMaxWidth(),
            videoUri = state.element?.videoUrl?.toUri(),
            isEditing = (isEditing != null && isEditing),
            onViewClick = {
                choseImage = it
                showDialog = true
            },
            onChooseVideo = {
                showBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = true
                }
            },
            onDeleteVideo = {
                onEvent(ElementDetailEvent.VideoChanged(null))
            }
        )

    }

    FullScreenVideoDialog(
        modifier = Modifier.fillMaxSize(),
        player = state.player,
        url = state.fullScreenVideoUrl ?: "",
        showDialog = state.showFullScreenVideo,
        onEvent = onPlayerEvent,
        onDismiss = {
            onEvent(ElementDetailEvent.ChangeFullScreenVideoState(null, false))
        }
    )

    if (showConfirmDialog) {
        SuccessDialog(
            onDismiss = {
                showConfirmDialog = false
                onMainEvent(MainEvent.ElementEditingChanged(null))
            },
            onSuccess = { onEvent(ElementDetailEvent.ChangeData) }
        )
    }

    if (state.isLoading) {
        LoadingIndicator(false)
    }

    if (showBottomSheet) {
        VideoPickerBottomSheet(
            videoUri = state.element?.videoUrl?.toUri(),
            onClose = {
                hideBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = false
                }
            },
            onVideoChosen = {
                onEvent(ElementDetailEvent.VideoChanged(it))
            }
        )
    }
}