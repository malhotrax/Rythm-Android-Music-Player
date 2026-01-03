package com.player.presentation.screen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.player.presentation.state.PlayerEvent
import com.player.presentation.state.PlayerVM
import com.player.presentation.ui.component.ImageContainer
import com.player.presentation.ui.component.PlayScreenTopBar
import com.player.presentation.ui.component.PlaybackController
import com.player.presentation.ui.component.ProgressBar
import com.player.presentation.ui.component.SongTitle

@Composable
fun PlayerScreen(
    playerVM: PlayerVM,
    navController: NavHostController
) {
    val state by playerVM.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            PlayScreenTopBar(
                navigation = {
                    navController.popBackStack()
                },
                likeOrNot = {
                    playerVM.onEvent(PlayerEvent.ToggleFavouriteSong(state.currentSong.id))
                },
                isLiked = state.currentSong.isFav
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp),
        ) {
            val (image, songTitle, progressBar, playbackController) = createRefs()

            ImageContainer(
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                size = 200,
                coverImage = playerVM.getAlbumArt(state.currentSong.path)
            )

            SongTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .basicMarquee()
                    .constrainAs(songTitle) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                title = state.currentSong.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            ProgressBar(
                modifier = Modifier
                    .constrainAs(progressBar) {
                        bottom.linkTo(playbackController.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                currentPosition = state.scrubPosition?.toFloat() ?: state.currentPosition.toFloat(),
                duration = state.duration.toFloat(),
                onValueChange = {
                    playerVM.onEvent(PlayerEvent.OnScrub(it.toLong()))
                },
                onValueChangeFinished = {
                    playerVM.onEvent(PlayerEvent.OnSeekFinished)
                }
            )

            PlaybackController(
                modifier = Modifier
                    .constrainAs(playbackController) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 60.dp)
                    },
                playPause = { playerVM.onEvent(PlayerEvent.PlayPause) },
                skipToNext = { playerVM.onEvent(PlayerEvent.NextSong) },
                skipToPrevious = { playerVM.onEvent(PlayerEvent.PreviousSong) },
                isPlaying = state.isPlaying,
                playPauseIconSize = 50,
                iconSize = 45,
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(viewModel(), rememberNavController())
}

