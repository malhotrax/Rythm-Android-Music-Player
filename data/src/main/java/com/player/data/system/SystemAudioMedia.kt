package com.player.data.system

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.player.data.local.table.MusicTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SystemAudioMedia @Inject constructor(
    private val contentResolver: ContentResolver
) {
    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA,
    )
    fun getAlbumArt(path: String): Bitmap? {
        val retriever = MediaMetadataRetriever()
        val albumArtByteArray: ByteArray? = retriever.embeddedPicture
        var albumArt: Bitmap? = null
        if (albumArtByteArray != null) {
            try {
                albumArt =
                    BitmapFactory.decodeByteArray(albumArtByteArray, 0, albumArtByteArray.size)
            } catch (e: Exception) {
                null
            } finally {
                retriever.release()
            }
        }
        return albumArt
    }

    suspend fun getMusicFromSystem(): List<MusicTable> {
        val musicList = mutableListOf<MusicTable>()
        return withContext(Dispatchers.IO) {

            contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null,
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val duration = cursor.getLong(durationColumn)
                    val data = cursor.getString(dataColumn)
                    musicList.add(
                        MusicTable(
                            id = id,
                            name = title,
                            duration = duration,
                            path = data,
                            isFav = false
                        )
                    )
                }
            }
            musicList
        }

    }

    fun getSystemMusicCount(): Int {
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,null,null,null
        )
        val count = cursor?.count
        cursor?.close()
        return count ?: 0
    }

}