package ua.wied.presentation.screens.instructions.video

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.repository.VideoPlayerEvent
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.domain.usecases.PlayVideoUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.video.model.VideoEvent
import ua.wied.presentation.screens.instructions.video.model.VideoState
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getInstructionFoldersUseCase: GetInstructionFoldersUseCase,
    private val playVideo: PlayVideoUseCase
) : BaseViewModel<VideoState, VideoEvent>(VideoState()) {

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

    override fun onEvent(event: VideoEvent) {
        when (event) {
            is VideoEvent.LoadData -> { initialize(event.value) }
            is VideoEvent.ChangeFullScreenVideoState -> {
                updateState { it.copy(
                    showFullScreenVideo = event.showDialog,
                    fullScreenVideoUrl = event.url
                ) }
            }
            is VideoEvent.Refresh -> { uiState.value.initInstruction?.let { initialize(it, true) } }
        }
    }

    private fun initialize(initInstruction: Instruction, isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getInstructionFoldersUseCase() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { folders ->
                val instructions = getInstructions(folders, initInstruction)
                updateState {
                    it.copy(
                        initInstruction = initInstruction,
                        instructions = instructions,
                        isEmpty = instructions.isEmpty(),
                        isNotInternetConnection = false
                    )
                }
            },
            onRefresh = { value ->
                if (isRefresh) {
                    if (!value) delay(100)
                    updateState { it.copy(isRefreshing = value ) }
                }
            }
        )
    }

    private fun getInstructions(
        folders: List<Folder<Instruction>>,
        initInstruction: Instruction
    ): List<Instruction> =
        listOf(initInstruction) +
                folders.asSequence()
                    .flatMap { it.items.asSequence() }
                    .filterNot { it == initInstruction }
                    .toList()


    override fun onCleared() {
        playVideo.release()
        super.onCleared()
    }

}