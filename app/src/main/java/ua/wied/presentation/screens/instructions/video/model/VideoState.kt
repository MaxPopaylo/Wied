package ua.wied.presentation.screens.instructions.video.model

import androidx.media3.exoplayer.ExoPlayer
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class VideoState (
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val player: ExoPlayer? = null,
    val showFullScreenVideo: Boolean = false,
    val fullScreenVideoUrl: String? = null,
    val initInstruction: Instruction? = null,
    val instructions: List<Instruction> = emptyList()
): BaseNetworkResponseState