package ua.wied.data.di.modules

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.wied.data.repository.CacheManager
import ua.wied.data.repository.PlayerFactory
import ua.wied.data.repository.VideoPlayerRepositoryImp
import ua.wied.domain.repository.VideoPlayerRepository
import javax.inject.Named
import javax.inject.Singleton

@OptIn(UnstableApi::class)
@Module
@InstallIn(SingletonComponent::class)
object MediaModule {
    private const val MAX_CACHE_SIZE: Long = 500L * 1024L * 1024L

    @Provides
    @Singleton
    @Named("UserAgent")
    fun provideUserAgent(@ApplicationContext context: Context): String =
        Util.getUserAgent(context, "WiED")

    @OptIn(UnstableApi::class)
    @Provides @Singleton
    fun provideSimpleCache(@ApplicationContext context: Context): SimpleCache {
        val cacheDir = context.cacheDir.resolve("media_cache")
        val databaseProvider = StandaloneDatabaseProvider(context)
        return SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE),
            databaseProvider
        )
    }

    @Provides
    @Singleton
    fun provideDataSourceFactory(
        @Named("UserAgent") userAgent: String,
        simpleCache: SimpleCache
    ): DataSource.Factory {
        val httpFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(userAgent)

        return CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    @Provides
    @Singleton
    fun provideMediaSourceFactory(
        dataSourceFactory: DataSource.Factory
    ): DefaultMediaSourceFactory =
        DefaultMediaSourceFactory(dataSourceFactory)

    @Provides
    @Singleton
    fun providePlayerFactory(
        @ApplicationContext context: Context,
        mediaSourceFactory: DefaultMediaSourceFactory
    ): PlayerFactory = PlayerFactory(context, mediaSourceFactory)

    @Provides
    @Singleton
    fun provideCacheManager(
        simpleCache: SimpleCache
    ): CacheManager = CacheManager(simpleCache)

    @Provides
    @Singleton
    fun provideVideoPlayerRepository(
        playerFactory: PlayerFactory,
        cacheManager: CacheManager
    ): VideoPlayerRepository = VideoPlayerRepositoryImp(playerFactory, cacheManager)
}
