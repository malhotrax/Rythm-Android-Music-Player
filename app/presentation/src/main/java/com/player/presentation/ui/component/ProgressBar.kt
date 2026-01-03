package com.player.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.player.presentation.util.Util

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    currentPosition: Float,
    duration: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = currentPosition,
            onValueChange = onValueChange,
            onValueChangeFinished = onValueChangeFinished,
            valueRange = 0f..duration,
            colors = SliderDefaults.colors(
                activeTrackColor = MaterialTheme.colorScheme.primary,     // Filled part
                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant, // Unfilled part
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            ),
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    modifier = Modifier.height(8.dp), // Thinner, more professional look
                    thumbTrackGapSize = 0.dp,         // Removes gap between thumb and track
                    trackInsideCornerSize = 0.dp      // Sharp or rounded inside
                )
            },
            thumb = {
                Box(
                    modifier = Modifier
                        .size(15.dp)                 // Smaller, modern thumb
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Util.calculateDuration(currentPosition.toLong()),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = Util.calculateDuration(duration.toLong()),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }


}