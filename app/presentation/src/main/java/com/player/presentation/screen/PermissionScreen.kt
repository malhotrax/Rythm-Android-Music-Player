package com.player.presentation.screen

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import com.player.presentation.R
import com.player.presentation.navigation.Home
import com.player.presentation.navigation.Permission
import com.player.presentation.ui.component.PermissionDialog


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(
    navController: NavHostController,
    audioPermissionState: PermissionState,
    notificationPermissionState: PermissionState,
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val audioPermissionPermanentlyDenied = false
    var hasAttempted by remember { mutableStateOf(false) }

    LaunchedEffect(audioPermissionState.status.isGranted) {
        if (audioPermissionState.status.isGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionState.launchPermissionRequest()
            }
            navController.navigate(Home) {
                popUpTo(Permission) { inclusive = true }
            }
        }

    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)

        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())

            ) {
                Spacer(Modifier.height(100.dp))
                Text(
                    text = "Rythm Music uses \n these permissions",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                Title(text = "Required permissions")
                Permission(
                    icon = R.drawable.music_icon,
                    title = "Music and audio",
                    subTitle = "Used to play audio files stored on your phone"
                )

                Title(text = "Optional permissions")
                Permission(
                    icon = R.drawable.notifications,
                    title = "Notification",
                    subTitle = "Used to continue and control playback when app is in background"
                )
            }

            Button(
                onClick = {
                    if (!audioPermissionState.status.isGranted) {
                        if (!audioPermissionState.status.shouldShowRationale && hasAttempted) {
                            showDialog = true
                        } else {
                            hasAttempted = true
                            audioPermissionState.launchPermissionRequest()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                notificationPermissionState.launchPermissionRequest()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .size(50.dp)
            ) {
                Text("Continue")
            }
        }
        if (showDialog) {
            PermissionDialog(
                onDismiss = {
                    showDialog = false
                },
                text = "In order to run this app properly, you need to grant the audio permissions",
                title = "Permission Required",
                goToAppSettings = {
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.fromParts("package", context.packageName, null)
                        context.startActivity(this)
                    }
                    showDialog = false
                },
                onOkClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = android.net.Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                },
                isPermanentlyDeclined = audioPermissionPermanentlyDenied
            )
        }
    }
}


@Composable
fun Permission(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    subTitle: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(icon), "Permission icon", Modifier
                .padding(8.dp)
                .size(30.dp)
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        modifier = modifier
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}