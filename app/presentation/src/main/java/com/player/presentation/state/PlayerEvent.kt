package com.player.presentation.state


sealed class PlayerEvent {
    data class PlaySong(val id: Long) : PlayerEvent()
    data class PlayFavSong(val id: Long) : PlayerEvent()
    object PlayPause : PlayerEvent()
    object NextSong : PlayerEvent()
    object PreviousSong : PlayerEvent()
    data class SeekTo(val position: Long) : PlayerEvent()
    data class OnScrub(val position: Long) : PlayerEvent()
    object OnSeekFinished : PlayerEvent()
    data class ToggleFavouriteSong(val id: Long) : PlayerEvent()
}
