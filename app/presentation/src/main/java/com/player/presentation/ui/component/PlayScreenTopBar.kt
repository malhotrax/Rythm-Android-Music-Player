package com.player.presentation.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.player.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreenTopBar(
    modifier: Modifier = Modifier,
    navigation: () -> Unit,
    likeOrNot: () -> Unit,
    isLiked: Boolean = false
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = navigation
            ) {
                Icon(painter = painterResource(R.drawable.back_icon), "Back to home")
            }

        },
        actions = {
            IconButton(onClick = likeOrNot) {
                val icon = if (isLiked) painterResource(R.drawable.like_icon)
                else painterResource(R.drawable.not_like_icon)
                Icon(icon, "Like song")
            }
        }
    )
}