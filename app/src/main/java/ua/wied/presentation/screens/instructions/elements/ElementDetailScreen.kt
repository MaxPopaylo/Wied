package ua.wied.presentation.screens.instructions.elements

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.wied.R
import ua.wied.domain.models.instruction.Element
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.FullScreenImageDialog
import ua.wied.presentation.common.composable.LargeVideoPicker
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailEvent

@Composable
fun ElementDetailScreen(
    element: Element,
    isEditing: Boolean,
    viewModel: ElementDetailViewModel = hiltViewModel()
) {
    var choseImage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ElementDetailEvent.LoadDate(element))
    }

    LaunchedEffect(isEditing) {
        if (isEditing) {
            showConfirmDialog = true
        }
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(top = dimen.containerPaddingLarge)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
    ) {
        DetailTextField(
            title = stringResource(R.string.title),
            text = state.element?.title ?: element.title,
            isEditing = isEditing,
            onTextChange = { value ->
                viewModel.onEvent(ElementDetailEvent.TitleChanged(value))
            }
        )

        (state.element?.info ?: element.info) ?.let {
            DetailTextField(
                title = stringResource(R.string.title),
                text = it,
                isEditing = isEditing,
                minHeight = 120.dp,
                onTextChange = { value ->
                    viewModel.onEvent(ElementDetailEvent.InfoChanged(value))
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
            videoUri = (state.element?.videoUrl ?: element.videoUrl)?.toUri(),
            isEditing = isEditing,
            onViewClick = {
                choseImage = it
                showDialog = true
            },
            onVideoChosen = {
                viewModel.onEvent(ElementDetailEvent.VideoChanged(it))
            },
            onDeleteVideo = {
                viewModel.onEvent(ElementDetailEvent.VideoChanged(null))
            }
        )

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
            onDismiss = { showConfirmDialog = false },
            onSuccess = { viewModel.onEvent(ElementDetailEvent.ChangeData) }
        )
    }

    if (state.isLoading) {
        LoadingIndicator()
    }
}