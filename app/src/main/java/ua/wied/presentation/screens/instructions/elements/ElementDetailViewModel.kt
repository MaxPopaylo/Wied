package ua.wied.presentation.screens.instructions.elements

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.wied.domain.repository.VideoPlayerEvent
import ua.wied.domain.usecases.PlayVideoUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailEvent
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailState
import javax.inject.Inject

@HiltViewModel
class ElementDetailViewModel @Inject constructor(
    private val playVideo: PlayVideoUseCase
): BaseViewModel<ElementDetailState, ElementDetailEvent>(ElementDetailState()) {

    init {
        updateState { it.copy(
            player = playVideo.createPlayer()
        ) }
    }

    fun onEvent(event: VideoPlayerEvent) {
        when (event) {
            is VideoPlayerEvent.Prepare -> playVideo.prepare(event.url)
            is VideoPlayerEvent.Play -> playVideo.play()
            is VideoPlayerEvent.Pause -> playVideo.pause()
            is VideoPlayerEvent.Stop -> playVideo.stop()
            is VideoPlayerEvent.Release -> playVideo.release()
        }
    }

    override fun onEvent(event: ElementDetailEvent) {
        when(event) {
            is ElementDetailEvent.LoadDate -> updateState { it.copy(element = event.element) }
            is ElementDetailEvent.TitleChanged -> updateState { it.copy(element = it.element?.copy(title = event.value)) }
            is ElementDetailEvent.InfoChanged -> updateState { it.copy(element = it.element?.copy(info = event.value)) }
            is ElementDetailEvent.VideoChanged -> updateState { it.copy(element = it.element?.copy(videoUrl = event.value)) }
            is ElementDetailEvent.ChangeFullScreenVideoState -> {
                updateState { it.copy(
                    showFullScreenVideo = event.showDialog,
                    fullScreenVideoUrl = event.url
                ) }
            }
            is ElementDetailEvent.ChangeData -> { changeElement() }
        }
    }

    private fun changeElement() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            delay(500)
            updateState { it.copy(isLoading = false) }
        }
    }
}