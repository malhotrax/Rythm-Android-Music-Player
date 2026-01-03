package com.player.presentation.module

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.player.presentation.player.repository.PlaybackController
import com.player.presentation.player.repository.impl.PlaybackControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Exoplayer {

    @Provides
    @Singleton
    fun providesExoplayer(@ApplicationContext context: Context) = ExoPlayer.Builder(context).build()

    @Provides
    @Singleton
    fun providesPlaybackController(@ApplicationContext context: Context) : PlaybackController {
        return PlaybackControllerImpl(context)
    }

}