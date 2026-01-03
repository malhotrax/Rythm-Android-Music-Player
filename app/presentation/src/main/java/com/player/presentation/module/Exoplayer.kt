package com.player.presentation.module

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
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
    fun providesExoplayer(@ApplicationContext context: Context) = ExoPlayer.Builder(context)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                .build(),
            true
        ).build()

    @Provides
    @Singleton
    fun providesPlaybackController(@ApplicationContext context: Context) : PlaybackController {
        return PlaybackControllerImpl(context)
    }

}