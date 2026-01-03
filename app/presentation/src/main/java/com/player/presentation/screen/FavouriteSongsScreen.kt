package com.player.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.player.presentation.state.PlayerEvent
import com.player.presentation.state.PlayerVM
import com.player.presentation.ui.component.SongWrapper

@Composable
fun FavouriteSongsScreen(
    playerVM: PlayerVM
) {
    val songs by playerVM.songs.collectAsState()
    val state by playerVM.state.collectAsState()
    val likedSongs = songs.filter { it.isFav }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)

    ) {
        val (noSongs, featureMusic) = createRefs()
        if (state.error != null) {
            Text(
                text = state.error.toString(),
                modifier = Modifier
                    .constrainAs(noSongs) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                style = MaterialTheme.typography.bodyLarge
            )
        } else if (likedSongs.isEmpty()) {
            Text(
                text = "No favourite song",
                modifier = Modifier
                    .constrainAs(noSongs) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(featureMusic) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    bottom = 80.dp
                )
            ) {
                items(likedSongs) { song ->
                    SongWrapper(
                        songTitle = song.name,
                        coverImage = song.cover,
                        onClickListener = {
                            playerVM.onEvent(PlayerEvent.PlayFavSong(song.id))
                        },
                        size = 30,
                    )
                }

            }
        }
    }

}