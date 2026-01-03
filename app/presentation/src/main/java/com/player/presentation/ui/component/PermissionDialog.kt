package com.player.presentation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.player.presentation.screen.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    text: String,
    title: String,
    isPermanentlyDeclined: Boolean,
    goToAppSettings: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(text)
        },
        title = {
            Text(title)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if(isPermanentlyDeclined) {
                        goToAppSettings()
                    } else {
                        onOkClick()
                    }
                }
            ) {
                Text("Grant permission")
            }

        }
    )
}