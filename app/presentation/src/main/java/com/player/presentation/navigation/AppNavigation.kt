package com.player.presentation.navigation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.player.presentation.screen.HomeScreen
import com.player.presentation.screen.PermissionScreen
import com.player.presentation.screen.PlayerScreen
import com.player.presentation.screen.SearchScreen
import com.player.presentation.state.PlayerVM

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val audioPermission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_AUDIO
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
    val notificationPermission: String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.POST_NOTIFICATIONS
    } else {
        null
    }
    val audioPermissionState = rememberPermissionState(audioPermission)
    val notificationPermissionState = rememberPermissionState(notificationPermission!!)
    val startDestination = if (checkPermission(context,audioPermission)) Home else Permission

    var playerVM: PlayerVM? = null

    if(audioPermissionState.status.isGranted) {
        playerVM = hiltViewModel()
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Home> {
            HomeScreen(
                navController = navController,
                playerVM = playerVM!!
            )
        }
        composable<Search> {
            SearchScreen(navController,playerVM!!)
        }
        composable<Player> {
            PlayerScreen(
                playerVM = playerVM!!,
                navController = navController
            )
        }
        composable<Permission> {
            PermissionScreen(
                navController = navController,
                audioPermissionState,
                notificationPermissionState
            )
        }
    }
}

fun checkPermission(context: Context,permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED
}