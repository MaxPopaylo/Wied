package ua.wied.domain.repository

import androidx.media3.exoplayer.ExoPlayer

interface VideoPlayerRepository {
    fun prepare(url: String)
    fun play()
    fun pause()
    fun stop()
    fun release()
    fun createPlayer(): ExoPlayer
}

sealed class VideoPlayerEvent() {
    data class Prepare(val url: String): VideoPlayerEvent()
    data object Play: VideoPlayerEvent()
    data object Pause: VideoPlayerEvent()
    data object Stop: VideoPlayerEvent()
    data object Release: VideoPlayerEvent()
}