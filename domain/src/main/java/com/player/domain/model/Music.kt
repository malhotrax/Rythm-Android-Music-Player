package com.player.domain.model

import android.graphics.Bitmap

data class Music(
    val id: Long = 0L,
    val path: String,
    val name: String,
    val duration: Long = 0L,
    val cover: Bitmap? = null,
    val isFav: Boolean = false
) {
    companion object {
        fun emptyMusicObject(): Music {
            return Music(
                id = 0L,
                path = "",
                name = "",
                duration = 0L,
                cover = null,
            )
        }
    }
}