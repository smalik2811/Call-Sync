package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.OnBoardViewModel
import com.yangian.callsync.feature.onboard.R
import qrcode.QRCode

@Composable
fun ConnectionScreen2(
    onBoardViewModel: OnBoardViewModel,
    updateOnBoardingCompleted: (boolean: Boolean, firebaseAnalytics: FirebaseAnalytics?) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context, firebaseAnalytics: FirebaseAnalytics) -> Unit,
    firebaseAnalytics: FirebaseAnalytics?,
    modifier: Modifier = Modifier,
) {

    val firebaseAuth = onBoardViewModel.firebaseAuth
    val currentUser = firebaseAuth.currentUser
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        if (currentUser == null) {
            CircularProgressIndicator()
        } else {

            val uid: String = currentUser.uid

            // generate and display the qr code
            val uidQRCode = QRCode
                .ofSquares()
                .withBackgroundColor(MaterialTheme.colorScheme.surface.toArgb())
                .withColor(MaterialTheme.colorScheme.onSurface.toArgb())
                .build(uid)

            OutlinedCard {
                Image(
                    bitmap = (uidQRCode.render().nativeImage() as Bitmap).asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    updateOnBoardingCompleted(true, firebaseAnalytics)
                    navigateToHome()
                    if (firebaseAnalytics != null) {
                        registerLogsDownloadWorkRequest(context, firebaseAnalytics)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Finish")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun ConnectionScreen2Preview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            ConnectionScreen2(
                onBoardViewModel = hiltViewModel(),
                updateOnBoardingCompleted = { _, _ -> },
                navigateToHome = {},
                registerLogsDownloadWorkRequest = { _, _ -> },
                firebaseAnalytics = null
            )
        }
    }
}