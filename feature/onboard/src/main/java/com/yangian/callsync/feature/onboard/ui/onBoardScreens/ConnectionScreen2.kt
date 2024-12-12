package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.MultiDevicePreview
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R
import qrcode.QRCode

@Composable
fun ColumnScope.CompactConnectionScreen2(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
) {
    val context = LocalContext.current

    Text(
        text = stringResource(id = R.string.connection1_desc),
        style = MaterialTheme.typography.titleLarge
    )

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

    Text(
        text = stringResource(id = R.string.finish_warning),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large)))

    OutlinedCard(
        modifier = Modifier.sizeIn(
            maxHeight = 350.dp,
            maxWidth = 350.dp
        )
    ) {
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

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large)))

    Button(
        onClick = {
            updateOnBoardingCompleted(true)
            navigateToHome()
            registerLogsDownloadWorkRequest(context)

        },
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(stringResource(R.string.finish))
    }
}

@Composable
fun ExpandedConnectionScreen2(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
) {
    val context = LocalContext.current

    OutlinedCard(
        modifier = Modifier.sizeIn(maxWidth = 400.dp, maxHeight = 400.dp)
    ) {
        Image(
            bitmap =
            getQRCode(
                MaterialTheme.colorScheme.surface.toArgb(),
                MaterialTheme.colorScheme.onSurface.toArgb()
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }

    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_large)))

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Text(
            text = stringResource(id = R.string.finish_warning),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large)))

        Button(
            onClick = {
                updateOnBoardingCompleted(true)
                navigateToHome()
                registerLogsDownloadWorkRequest(context)

            },
        ) {
            Text(stringResource(R.string.finish))
        }
    }
}

@Composable
fun ConnectionScreen2(
    scaffoldingPadding: PaddingValues,
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val screenOrientation = LocalContext.current.resources.configuration.orientation

    when (screenOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(scaffoldingPadding)
                    .windowInsetsPadding(WindowInsets.safeContent)
            ) {
                ExpandedConnectionScreen2(
                    getQRCode,
                    updateOnBoardingCompleted,
                    navigateToHome,
                    registerLogsDownloadWorkRequest,
                )
            }
        }

        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CompactConnectionScreen2(
                    getQRCode,
                    updateOnBoardingCompleted,
                    navigateToHome,
                    registerLogsDownloadWorkRequest
                )
            }
        }
    }
}

@MultiDevicePreview
@Composable
private fun ExpandedConnectionScreen2Preview() {
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
            Column {
                ConnectionScreen2(
                    PaddingValues(),
                    getQRCode = { _, _ ->
                        imageBitMap
                    },
                    updateOnBoardingCompleted = { _ -> },
                    navigateToHome = {},
                    registerLogsDownloadWorkRequest = { _ -> },
                )
            }
        }
    }
}