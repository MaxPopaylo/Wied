package ua.wied.presentation.common.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import ua.wied.domain.repository.VideoPlayerEvent
import ua.wied.presentation.common.theme.WiEDTheme.dimen


@Composable
fun FullScreenVideoDialog(
    modifier: Modifier = Modifier,
    url: String,
    showDialog: Boolean = false,
    onDismiss: () -> Unit,
    onEvent: (VideoPlayerEvent) -> Unit,
    player: ExoPlayer?
) {
    LaunchedEffect(url) {
        onEvent(VideoPlayerEvent.Prepare(url))
        onEvent(VideoPlayerEvent.Play)
    }

    if (showDialog) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            ),
            onDismissRequest = {
                onEvent(VideoPlayerEvent.Stop)
                onDismiss()
            }
        ) {
            Surface(
                modifier = modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.onBackground
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AndroidView(
                        factory = { ctx ->
                            PlayerView(ctx).apply {
                                this.player = player
                                useController = true
                                layoutParams = android.view.ViewGroup.LayoutParams(
                                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = {
                            onEvent(VideoPlayerEvent.Stop)
                            onDismiss()
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(dimen.padding2Xl)
                    ) {
                        Icon(
                            modifier = Modifier.size(dimen.sizeL),
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
