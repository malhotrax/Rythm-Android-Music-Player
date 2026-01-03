package com.player.presentation.player.repository

import androidx.media3.common.Player
import com.player.domain.model.Music

interface PlaybackController {
    fun setPlaylist(songs: List<Music>)
    fun play()
    fun pause()
    fun playAt(index: Int, position: Long)
    fun seekTo(position: Long)
    fun skipToNext()
    fun skipToPrevious()
    fun addListener(listener: Player.Listener)
    fun removeListener(listener: Player.Listener)

    fun getMediaCount(): Int
    fun isPlaying(): Boolean
    fun getCurrentPosition(): Long
    fun getDuration(): Long


}