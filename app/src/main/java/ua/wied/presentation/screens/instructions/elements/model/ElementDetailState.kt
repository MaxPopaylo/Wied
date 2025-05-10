package ua.wied.presentation.screens.instructions.elements.model

import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.instruction.Element
import ua.wied.presentation.common.base.BaseState

data class ElementDetailState (
    override val isLoading: Boolean = false,
    val updateResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val player: ExoPlayer? = null,
    val showFullScreenVideo: Boolean = false,
    val fullScreenVideoUrl: String? = null,
    val element: Element? = null
): BaseState