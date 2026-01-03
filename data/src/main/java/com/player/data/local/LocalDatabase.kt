package com.player.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.player.data.local.dao.MusicDao
import com.player.data.local.table.MusicTable

@Database(entities = [MusicTable::class], version = 3, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}