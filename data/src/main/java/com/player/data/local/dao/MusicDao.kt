package com.player.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.player.data.local.table.MusicTable
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {
    @Query("SELECT id FROM music_table")
    suspend fun getAllIds(): List<Long>

    @Query("SELECT * FROM music_table")
    fun getAllMusic(): Flow<List<MusicTable>>

    @Query("SELECT * FROM music_table WHERE id = :id")
    fun getMusicById(id: Long): Flow<MusicTable>
    @Query("UPDATE music_table SET isFav = NOT isFav WHERE id = :id")
    suspend fun toggleFavouriteSong(id: Long)

    @Query("DELETE FROM music_table WHERE id  IN (:idsToDelete)")
    suspend fun deleteSpecificSongs(idsToDelete: List<Long>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewSongs(songs: List<MusicTable>)

    @Transaction
    suspend fun performDiffSync(systemSongs: List<MusicTable>) {
        val localIds  = getAllIds().toSet()
        val systemIds = systemSongs.map { it.id }.toSet()
        val idsToDelete = localIds.filter { it !in systemIds }
        if(idsToDelete.isNotEmpty()) {
            deleteSpecificSongs(idsToDelete)
        }
        val songsToInsert = systemSongs.filter { it.id !in localIds }
        if(songsToInsert.isNotEmpty()) {
            insertNewSongs(songsToInsert)
        }
    }

    @Query("DELETE FROM music_table WHERE id IS :songId")
    suspend fun deleteById(songId: Long)
}
