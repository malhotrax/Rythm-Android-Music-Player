package com.player.presentation.state

import com.player.domain.model.Music

data class PlayerState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentSong: Music = Music.emptyMusicObject(),
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val scrubPosition: Long? = null,
    val error: Throwable? = null
)