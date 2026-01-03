package com.player.presentation.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import coil3.Bitmap
import com.player.domain.model.Music
import com.player.domain.repository.MusicRepository
import com.player.domain.util.Resource
import com.player.presentation.player.PlaybackContext
import com.player.presentation.player.repository.PlaybackController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerVM @Inject constructor(
    private val musicRepository: MusicRepository,
    private val playbackController: PlaybackController,
) : ViewModel() {
    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()
    private val _songs = MutableStateFlow<List<Music>>(emptyList())
    val songs = _songs.asStateFlow()
    private var progressJob: Job? = null

    private var currentContext: PlaybackContext = PlaybackContext.ALL_SONGS

    fun loadMusic() {
        viewModelScope.launch {
            musicRepository.getMusicList().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _songs.value = result.data ?: emptyList()
                    }

                    is Resource.Error -> {
                        _songs.value = emptyList()
                        _state.value = _state.value.copy(
                            error = result.err
                        )
                    }

                    is Resource.Loading -> {
                        _songs.value = result.data!!
                    }
                }
            }
        }
    }

    fun onEvent(event: PlayerEvent) {
        when (event) {
            PlayerEvent.NextSong -> skipToNext()
            PlayerEvent.PlayPause -> playPause()
            is PlayerEvent.PlaySong -> playSong(event.id)
            PlayerEvent.PreviousSong -> skipToPrevious()
            is PlayerEvent.SeekTo -> seekTo(event.position)
            is PlayerEvent.OnScrub -> onScrub(event.position)
            PlayerEvent.OnSeekFinished -> onSeekFinished()
            is PlayerEvent.ToggleFavouriteSong -> onToggleFavouriteSong(event.id)
            is PlayerEvent.PlayFavSong -> playFavSong(event.id)
        }
    }

    private fun setUpPlayListOnContextChange(
        context: PlaybackContext,
        songs: List<Music>
    ) {
        if (context != currentContext || playbackController.getMediaCount() == 0) {
            currentContext = context
            setupPlaylist(songs)
        }
    }

    private fun onScrub(position: Long) {
        _state.value = _state.value.copy(
            scrubPosition = position
        )
    }

    private fun onToggleFavouriteSong(songId: Long) = viewModelScope.launch {
        musicRepository.toggleFavouriteSong(songId)
        _state.value = _state.value.copy(
            currentSong = _state.value.currentSong.copy(
                isFav = !_state.value.currentSong.isFav
            )
        )
    }

    private fun onSeekFinished() {
        val targetPosition = _state.value.scrubPosition ?: _state.value.currentPosition
        playbackController.seekTo(targetPosition)
        playbackController.play()
        _state.value = _state.value.copy(
            scrubPosition = null,
            currentPosition = targetPosition,
            isPlaying = true
        )
    }

    private fun startProgressUpdate() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            while (playbackController.isPlaying()) {
                _state.value = _state.value.copy(
                    currentPosition = playbackController.getCurrentPosition(),
                    duration = playbackController.getDuration()
                )
                delay(500)
            }
        }
    }

    private fun playSong(id: Long) {
        setUpPlayListOnContextChange(
            context = PlaybackContext.ALL_SONGS,
            songs = _songs.value
        )
        val index = _songs.value.indexOfFirst { it.id == id }
        if (index != -1) {
            playbackController.playAt(index, 0L)
            _state.value = _state.value.copy(
                currentPosition = 0L,
                currentSong = _songs.value[index],
                isPlaying = true
            )
            startProgressUpdate()
        }
    }

    private fun playFavSong(id: Long) {
        val favSongs = _songs.value.filter { it.isFav }
        setUpPlayListOnContextChange(
            context = PlaybackContext.LIKED_SONGS,
            songs = favSongs
        )
        val index = favSongs.indexOfFirst { it.id == id }
        if (index != -1) {
            playbackController.playAt(index, 0L)
            _state.value = _state.value.copy(
                currentPosition = 0L,
                currentSong = favSongs[index],
            )
            startProgressUpdate()
        }
    }

    private fun playPause() {
        if (state.value.isPlaying) {
            playbackController.pause()
        } else {
            playbackController.play()
        }
    }

    private fun setupPlaylist(songs: List<Music>) {
        playbackController.setPlaylist(songs)
    }

    private fun seekTo(position: Long) {
        playbackController.seekTo(position)

    }

    private fun skipToNext() {
        playbackController.skipToNext()
    }

    private fun skipToPrevious() {
        playbackController.skipToPrevious()
    }


    private val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }

                Player.STATE_READY -> {
                }

                Player.STATE_ENDED -> {
                    _state.value = _state.value.copy(
                        isPlaying = false
                    )
                }

                Player.STATE_IDLE -> {}
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            _state.value = _state.value.copy(
                isPlaying = isPlaying
            )
            if (isPlaying) {
                startProgressUpdate()
            } else {
                progressJob?.cancel()
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            val currentMediaId = mediaItem?.mediaId?.toLongOrNull()
            val index = _songs.value.indexOfFirst { it.id == currentMediaId }
            if (index != -1) {
                _state.value = _state.value.copy(
                    currentSong = _songs.value[index],
                    duration = _songs.value[index].duration
                )
            }
        }

    }

    init {
        playbackController.addListener(listener)
        loadMusic()
    }

    override fun onCleared() {
        super.onCleared()
        progressJob?.cancel()
        playbackController.removeListener(listener)
    }

    fun getAlbumArt(path: String): Bitmap? {
        var albumArt: Bitmap? = null
        viewModelScope.launch {
            albumArt = musicRepository.getAlbumArt(path)
        }
        return albumArt
    }

}