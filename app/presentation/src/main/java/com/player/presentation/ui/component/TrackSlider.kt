package com.player.presentation.ui.component

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TrackSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
) {
    Slider(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
    )
}