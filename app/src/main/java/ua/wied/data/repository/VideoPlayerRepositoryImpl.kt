package ua.wied.data.repository

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.wied.domain.repository.VideoPlayerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPlayerRepositoryImp @Inject constructor(
    private val playerFactory: PlayerFactory,
    private val cacheManager: CacheManager
) : VideoPlayerRepository {
    private var player: ExoPlayer? = null

    override fun prepare(url: String) {
        if (player == null) {
            player = playerFactory.createPlayer()
        }
        val mediaItem = playerFactory.buildMediaItem(url)
        player?.apply {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    override fun play() {
        player?.play()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
    }

    override fun release() {
        player?.let {
            playerFactory.releasePlayer(it)
            player = null
        }
        cacheManager.releaseCache()
    }

    override fun createPlayer(): ExoPlayer {
        val pl = playerFactory.createPlayer()
        player = pl
        return pl
    }
}


@OptIn(UnstableApi::class)
@Singleton
class CacheManager @Inject constructor(
    private val simpleCache: SimpleCache
) {
    fun releaseCache() {
        simpleCache.release()
    }
}


@OptIn(UnstableApi::class)
@Singleton
class PlayerFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaSourceFactory: DefaultMediaSourceFactory
) {
    data class Settings(
        val playWhenReady: Boolean = true,
        val repeatMode: Int = ExoPlayer.REPEAT_MODE_OFF,
        val maxVideoSizeSd: Boolean = true
    )

    fun createPlayer(
        settings: Settings = Settings()
    ): ExoPlayer {
        val selector = DefaultTrackSelector(context).apply {
            if (settings.maxVideoSizeSd) {
                setParameters(buildUponParameters().setMaxVideoSizeSd())
            }
        }

        return ExoPlayer.Builder(context)
            .setTrackSelector(selector)
            .setMediaSourceFactory(mediaSourceFactory)
            .build().apply {
                playWhenReady = settings.playWhenReady
                repeatMode = settings.repeatMode
            }
    }

    fun buildMediaItem(url: String): MediaItem = MediaItem.fromUri(url)

    fun releasePlayer(player: ExoPlayer) {
        player.release()
    }
}