package com.player.data.local.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_table")
data class MusicTable(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val path: String,
    val name: String,
    val duration: Long,
    val isFav: Boolean
)