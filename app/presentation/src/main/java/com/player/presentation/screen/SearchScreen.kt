package com.player.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.player.domain.model.Music
import com.player.presentation.R
import com.player.presentation.state.PlayerEvent
import com.player.presentation.state.PlayerVM
import com.player.presentation.ui.component.SongWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    playerVM: PlayerVM
) {
    var textState by rememberSaveable {
        mutableStateOf("")
    }
    val songs by playerVM.songs.collectAsState()
    val result: List<Music> = if (textState.isBlank()) {
        emptyList()
    } else {
        songs.filter {
            it.name.lowercase().contains(textState.lowercase())
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(8.dp),
                title = {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = textState,
                        onValueChange = {
                            textState = it
                        },
                        placeholder = {
                            Text(
                                text = "Search...",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Left
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(painterResource(R.drawable.back_icon), "Go back")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            if (result.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No song found with $textState",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(result) { song ->
                        SongWrapper(
                            songTitle = song.name,
                            coverImage = playerVM.getAlbumArt(song.path),
                            onClickListener = {
                                playerVM.onEvent(PlayerEvent.PlaySong(song.id))
                            },
                            size = 30
                        )
                    }
                }
            }
        }
    }

}