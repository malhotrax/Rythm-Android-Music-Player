package com.player.presentation.ui.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.player.presentation.R

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    songTitle: String,
    playPause: () -> Unit,
    isPlaying: Boolean,
    coverImage: Bitmap? = null,
    skipToNext: () -> Unit,
    skipToPrevious: () -> Unit,
    onClickListener: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(35.dp)),
        color = MaterialTheme.colorScheme.primary,
        onClick = onClickListener
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageContainer(size = 30, coverImage = coverImage, shape = CircleShape)
            SongTitle(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .basicMarquee(),
                title = songTitle
            )
            IconButton(
                onClick = skipToPrevious
            ) {
                Icon(painterResource(R.drawable.skip_previous), "Skip to previous")
            }
            IconButton(
                onClick = playPause
            ) {
                val icon = if (isPlaying) painterResource(R.drawable.pause_icon)
                else painterResource(R.drawable.play_icon)

                Icon(icon, "Play and pause")
            }
            IconButton(
                onClick = skipToNext
            ) {
                Icon(painterResource(R.drawable.skip_next), "Skip to next")
            }
        }
    }
}

