package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
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
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large)))

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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.aspectRatio(1f, true)
    ) {
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
    }

    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_large)))

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

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

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
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
                modifier = modifier
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

@Preview(device = "spec:parent=pixel_5")
@Composable
private fun CompactConnectionScreen2Preview() {
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

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
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