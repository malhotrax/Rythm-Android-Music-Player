package com.player.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.player.presentation.R

@Composable
fun PlaybackController(
    modifier: Modifier = Modifier,
    playPause: () -> Unit,
    skipToNext: () -> Unit,
    skipToPrevious: () -> Unit,
    isPlaying: Boolean,
    iconSize: Int = 30,
    playPauseIconSize: Int = 30,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(R.drawable.skip_previous),
            "Skip to previous",
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .size(iconSize.dp)
                .clickable(onClick = skipToPrevious)

        )

        val playPauseIcon = if (isPlaying) painterResource(R.drawable.pause_icon)
        else painterResource(R.drawable.play_icon)

        Icon(
            playPauseIcon,
            "Play and pause",
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .size(playPauseIconSize.dp)
                .clickable(onClick = playPause)
        )
        Icon(
            painterResource(R.drawable.skip_next),
            "Skip to next",
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .size(iconSize.dp)
                .clickable(onClick = skipToNext)

        )

    }
}