package com.yangian.callsync.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.yangian.callsync.core.designsystem.icon.LogoutIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Sign out")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Sign out")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun CustomAlertDialogPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            CustomAlertDialog(
                onDismissRequest = { },
                onConfirmation = { },
                dialogTitle = "Are you sure you want to sign out?",
                dialogText = "Once signed out, you will no longer receive latest call logs and the existing logs will be deleted.",
                icon = LogoutIcon
            )
        }
    }
}