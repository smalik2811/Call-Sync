package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R
import qrcode.QRCode

@Composable
fun ConnectionScreen2(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean, firebaseAnalytics: FirebaseAnalytics?) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context, firebaseAnalytics: FirebaseAnalytics) -> Unit,
    firebaseAnalytics: FirebaseAnalytics?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        
        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        // generate and display the qr code

        OutlinedCard {
            Image(
                bitmap =
                getQRCode(
                    MaterialTheme.colorScheme.surface.toArgb(),
                    MaterialTheme.colorScheme.onSurface.toArgb()
                ),
                contentDescription = null,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Button(
            onClick = {
                updateOnBoardingCompleted(true, firebaseAnalytics)
                navigateToHome()
                if (firebaseAnalytics != null) {
                    registerLogsDownloadWorkRequest(context, firebaseAnalytics)
                }
            },
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(stringResource(R.string.finish))
        }

        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@Preview
@Composable
private fun ConnectionScreen2Preview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
            val foregroundColor = MaterialTheme.colorScheme.onSurface.toArgb()
            val byteArray: ByteArray = QRCode
                .ofSquares()
                .withBackgroundColor(backgroundColor)
                .withColor(foregroundColor)
                .build(stringResource(R.string.test_user))
                .renderToBytes()

            val imageBitMap = BitmapFactory
                .decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
            ConnectionScreen2(
                getQRCode = { _, _ ->
                    imageBitMap
                },
                updateOnBoardingCompleted = { _, _ -> },
                navigateToHome = {},
                registerLogsDownloadWorkRequest = { _, _ -> },
                firebaseAnalytics = null
            )
        }
    }
}