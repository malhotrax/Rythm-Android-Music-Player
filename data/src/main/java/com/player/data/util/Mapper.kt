package com.player.data.util

import com.player.data.local.table.MusicTable
import com.player.domain.model.Music

object Mapper {
    fun MusicTable.toDomain(): Music {
        return Music(
            id = this.id,
            name = this.name,
            duration = this.duration,
            path = this.path,
            cover = null,
            isFav = this.isFav
        )
    }

    fun List<MusicTable>.toDomain(): List<Music> {
        return this.map { it.toDomain() }
    }
}