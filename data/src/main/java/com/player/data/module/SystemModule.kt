package com.player.data.module

import android.content.ContentResolver
import android.content.Context
import com.player.data.system.SystemAudioMedia
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemModule {

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideSystemAudioMedia(contentResolver: ContentResolver): SystemAudioMedia {
        return SystemAudioMedia(contentResolver)
    }

}