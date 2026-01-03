package com.player.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.player.presentation.navigation.Player
import com.player.presentation.navigation.Search
import com.player.presentation.state.PlayerEvent
import com.player.presentation.state.PlayerVM
import com.player.presentation.ui.component.MiniPlayer
import com.player.presentation.ui.component.TopNavigationBar
import com.player.presentation.util.PrimaryTabs
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    playerVM: PlayerVM
) {
    val primaryTabs = PrimaryTabs.entries
    val pagerState = rememberPagerState(pageCount = { primaryTabs.size })
    val scope = rememberCoroutineScope()
    val state by playerVM.state.collectAsState()
    val showMiniPlayer = state.currentSong.path.isNotEmpty()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopNavigationBar(
                title = "Rythm Music",
                onSearch = {
                    navController.navigate(Search)
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(contentPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryTabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = {},
                    divider = {},
                    containerColor = Color.Transparent
                ) {
                    primaryTabs.forEachIndexed { index, tabs ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                val style: TextStyle = if (pagerState.currentPage == index) {
                                    MaterialTheme.typography.titleLarge
                                } else {
                                    MaterialTheme.typography.bodyMedium
                                }
                                val color = if (pagerState.currentPage == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                }
                                Text(
                                    text = tabs.title,
                                    style = style,
                                    color = color
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .clip(RoundedCornerShape(35.dp))
                        .shadow(
                            25.dp,
                            ambientColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            spotColor = MaterialTheme.colorScheme.surfaceContainerHighest
                        )
                        .background(MaterialTheme.colorScheme.background)
                ) { pageIndex ->
                    when (primaryTabs[pageIndex]) {
                        PrimaryTabs.FeatureMusic -> FeatureMusicScreen(playerVM)
                        PrimaryTabs.Favourite -> FavouriteSongsScreen(playerVM)
                    }
                }
            }
            if (showMiniPlayer) {
                MiniPlayer(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    songTitle = state.currentSong.name,
                    playPause = { playerVM.onEvent(PlayerEvent.PlayPause) },
                    isPlaying = state.isPlaying,
                    coverImage = state.currentSong.cover,
                    skipToNext = { playerVM.onEvent(PlayerEvent.NextSong) },
                    skipToPrevious = { },
                    onClickListener = {
                        navController.navigate(Player)
                    }
                )
            }
        }
    }
}