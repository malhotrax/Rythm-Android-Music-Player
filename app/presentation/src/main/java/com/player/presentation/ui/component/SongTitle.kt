package com.player.presentation.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.player.presentation.util.Util

@Composable
fun SongTitle(
    modifier: Modifier = Modifier,
    title: String,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Text(
        text = Util.truncateName(title),
        style = style,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        textAlign = textAlign,
    )
}