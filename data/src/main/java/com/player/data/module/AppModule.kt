package com.player.data.module

import android.content.Context
import androidx.room.Room
import com.player.data.local.LocalDatabase
import com.player.data.local.dao.MusicDao
import com.player.data.repository.MusicRepositoryImpl
import com.player.data.system.SystemAudioMedia
import com.player.domain.repository.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideMusicDao(localDatabase: LocalDatabase): MusicDao {
        return localDatabase.musicDao()
    }

    @Provides
    @Singleton
    fun provideMusicRepository(
        musicDao: MusicDao,
        systemAudioMedia: SystemAudioMedia
    ): MusicRepository {
        return MusicRepositoryImpl(
            musicDao,
            systemAudioMedia
        )
    }
}