package ua.wied.presentation.common.composable

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import ua.wied.R
import ua.wied.domain.repository.VideoPlayerEvent
import ua.wied.presentation.common.theme.WiEDTheme

@Composable
fun SuccessDialog(
    isDelete: Boolean = false,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        ElevatedCard(
            colors = CardColors(
                containerColor = WiEDTheme.colors.primaryBackground,
                contentColor = WiEDTheme.colors.primaryText,
                disabledContainerColor = WiEDTheme.colors.primaryBackground,
                disabledContentColor = WiEDTheme.colors.primaryText
            )
        ) {
            Column(
                modifier = Modifier.Companion.padding(WiEDTheme.dimen.containerPadding),
                verticalArrangement = Arrangement.spacedBy(WiEDTheme.dimen.paddingL)
            ) {
                Text(
                    text = if (isDelete) stringResource(R.string.confirm_delete_message)
                    else stringResource(R.string.confirm_changes_message),
                    style = WiEDTheme.typography.h5
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(WiEDTheme.dimen.paddingL)
                ) {
                    PrimaryButton(
                        modifier = Modifier.Companion.weight(1f),
                        title = stringResource(R.string.confirm_button),
                        onClick = {
                            onSuccess()
                            onDismiss()
                        }
                    )

                    SecondaryButton(
                        modifier = Modifier.Companion.weight(1f),
                        title = stringResource(R.string.cancel),
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}

@Composable
fun FullScreenImageDialog(
    url: String,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            Modifier.Companion
                .fillMaxSize()
                .background(
                    Color(0x80000000)
                ),
            contentAlignment = Alignment.Companion.Center
        ) {
            Box(
                modifier = Modifier.Companion
                    .fillMaxWidth(.9f)
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = "Full Screen Image",
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(4f / 2.5f)
                        .clickable { onDismissRequest() }
                )
            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.Companion.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.Companion.White
                )
            }
        }
    }
}

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
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
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
                            .padding(WiEDTheme.dimen.padding2Xl)
                    ) {
                        Icon(
                            modifier = Modifier.Companion.size(WiEDTheme.dimen.sizeL),
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