package com.player.domain.repository

import android.graphics.Bitmap
import com.player.domain.model.Music
import com.player.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    fun getMusicList(): Flow<Resource<List<Music>>>
    fun getMusic(id: Long): Flow<Resource<Music>>
    suspend fun getAlbumArt(path: String): Bitmap?
    suspend fun toggleFavouriteSong(id: Long)
}