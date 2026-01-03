package com.player.mp3.musicplayer.rythm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.player.presentation.navigation.AppNavigation
import com.player.presentation.ui.theme.RythmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RythmTheme {
                AppNavigation()
            }
        }
    }
}
