package ua.wied.presentation.screens.instructions.video

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.repository.VideoPlayerEvent
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.FullScreenVideoDialog
import ua.wied.presentation.common.composable.GridVideoItem
import ua.wied.presentation.common.theme.WiEDTheme
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.instructions.video.model.VideoEvent
import ua.wied.presentation.screens.instructions.video.model.VideoState
import kotlin.collections.forEach

@Composable
fun VideoScreen(
    initInstruction: Instruction,
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
    onPlayerEvent: (VideoPlayerEvent) -> Unit,
    onBackToInstructions: () -> Unit
) {
    BackHandler {
        onBackToInstructions()
    }

    LaunchedEffect(initInstruction) {
        onEvent(VideoEvent.LoadData(initInstruction))
    }

    ContentBox(
        state = state,
        emptyScreen = {
            InstructionEmptyScreen(
                isManager = false,
                isFiltered = false,
                onCreationClick = {}
        ) },

        onRefresh = { onEvent(VideoEvent.Refresh) }
    ) {
        VideoList(
            instructions = state.instructions,
            onViewVideoClick = {
                onEvent(VideoEvent.ChangeFullScreenVideoState(it, true))
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
            onEvent(VideoEvent.ChangeFullScreenVideoState(null, false))
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            onPlayerEvent(VideoPlayerEvent.Release)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun  VideoList (
    modifier: Modifier = Modifier,
    instructions: List<Instruction>,
    onViewVideoClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        instructions.forEach { instruction ->
            if (instruction.elements.isNotEmpty()) {
                stickyHeader(key = "${instruction.id}") {
                    VideoListHeader(instruction.title)
                }

                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimen.paddingL),
                        horizontalArrangement = Arrangement.spacedBy(dimen.paddingM)
                    ) {
                        items(
                            items = instruction.elements,
                            key = { element -> "${instruction.id}-${element.id}" }
                        ) { element ->
                            element.videoUrl?.let { url ->
                                GridVideoItem(
                                    modifier = Modifier
                                        .height(90.dp),
                                    title = element.title,
                                    videoUrl = url,
                                    onViewClick = { onViewVideoClick(url) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun VideoListHeader(
    text: String
) {
    val typography = WiEDTheme.typography
    val headerTextStyle = remember { typography.h4 }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.primaryBackground)
            .padding(horizontal = dimen.zero, vertical = dimen.paddingS)
    ) {
        Text(
            text = text,
            style = headerTextStyle
        )
    }

}