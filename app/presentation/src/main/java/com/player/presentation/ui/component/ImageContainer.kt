package com.player.presentation.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.player.presentation.R

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    coverImage: Bitmap? = null,
    size: Int = 0,
    shape: CornerBasedShape = RoundedCornerShape(15.dp),
    alternateImage: Int = R.drawable.music_icon
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (coverImage != null) {
            AsyncImage(
                model = coverImage, "Music player",
                contentScale = ContentScale.Fit,
            )
        } else {
            Image(
                painterResource(alternateImage),
                "Music cover",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
                modifier = Modifier.size(size.dp)
            )
        }
    }
}