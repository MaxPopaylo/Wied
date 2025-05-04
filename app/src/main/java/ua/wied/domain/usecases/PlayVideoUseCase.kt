package ua.wied.domain.usecases

import ua.wied.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class PlayVideoUseCase @Inject constructor(
    private val videoPlayer: VideoPlayerRepository
) {
    fun createPlayer() = videoPlayer.createPlayer()

    fun prepare(url: String) {
        videoPlayer.prepare(url)
    }

    fun play() {
        videoPlayer.play()
    }

    fun pause() {
        videoPlayer.pause()
    }

    fun stop() {
        videoPlayer.stop()
    }

    fun release() {
        videoPlayer.release()
    }
}