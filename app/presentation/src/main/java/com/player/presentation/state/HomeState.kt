package com.player.presentation.state

import com.player.domain.model.Music

data class HomeState(
    val songs: List<Music> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)