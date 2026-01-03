package com.player.presentation.ui.component

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.player.presentation.R

@Composable
fun SongWrapper(
    modifier: Modifier = Modifier,
    songTitle: String,
    coverImage: Bitmap? = null,
    onClickListener: () -> Unit,
    size: Int = 0
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp))
            .combinedClickable(
                onClick = onClickListener,
            ),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageContainer(size = size, coverImage = coverImage)
            SongTitle(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 8.dp),
                title = songTitle
            )
        }

    }
}

