package com.player.data.repository

import android.graphics.Bitmap
import android.util.Log
import androidx.collection.intSetOf
import com.player.data.local.dao.MusicDao
import com.player.data.system.SystemAudioMedia
import com.player.data.util.Mapper.toDomain
import com.player.data.util.mediaBondResource
import com.player.domain.model.Music
import com.player.domain.repository.MusicRepository
import com.player.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MusicRepositoryImpl(
    private val musicDao: MusicDao,
    private val systemAudioMedia: SystemAudioMedia
) : MusicRepository {
    override  fun getMusicList(): Flow<Resource<List<Music>>> {
        return mediaBondResource(
            query = {
                musicDao.getAllMusic().map { musicTable ->
                    musicTable.toDomain()
                }
            },
            fetch = {
                systemAudioMedia.getMusicFromSystem()
            },
            saveFetchResult = {
                musicDao.performDiffSync(it)
            },
            shouldFetch = { localList ->
                systemAudioMedia.getSystemMusicCount() != localList.size || localList.isEmpty()
            }
        )
    }

    override fun getMusic(id: Long): Flow<Resource<Music>> {
        return musicDao.getMusicById(id).map {
            Resource.Success(it.toDomain())
        }
    }

    override suspend fun getAlbumArt(path: String): Bitmap? {
        return systemAudioMedia.getAlbumArt(path)
    }

    override suspend fun toggleFavouriteSong(id: Long) {
        return musicDao.toggleFavouriteSong(id)
    }

}