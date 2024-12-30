package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.MultiDevicePreview
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.feature.onboard.R
import qrcode.QRCode

@Composable
fun CompactPortraitConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.weight(10f))

        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.finish_warning),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.weight(2f))

        OutlinedCard(
            modifier = Modifier
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

        Spacer(modifier = Modifier.weight(2f))

        Button(
            onClick = {
                updateOnBoardingCompleted(true)
                navigateToHome()
                registerLogsDownloadWorkRequest(context)

            },
        ) {
            Text(stringResource(R.string.finish))
        }

        Spacer(modifier = Modifier.weight(10f))
    }

}

@Composable
fun MediumPortraitConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.weight(10f))

        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.finish_warning),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.weight(3f))

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(0.6f)
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

        Spacer(modifier = Modifier.weight(4f))

        Button(
            onClick = {
                updateOnBoardingCompleted(true)
                navigateToHome()
                registerLogsDownloadWorkRequest(context)

            },
        ) {
            Text(
                text = stringResource(R.string.finish),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun ExpandedPortraitConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.weight(10f))

        Text(
            text = stringResource(id = R.string.connection1_desc),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.6f)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.finish_warning),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.6f)
        )

        Spacer(modifier = Modifier.weight(3f))

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(0.5f)
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

        Spacer(modifier = Modifier.weight(4f))

        Button(
            onClick = {
                updateOnBoardingCompleted(true)
                navigateToHome()
                registerLogsDownloadWorkRequest(context)

            },
        ) {
            Text(
                text = stringResource(R.string.finish),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun PortraitConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            CompactPortraitConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }

        WindowWidthSizeClass.MEDIUM -> {
            MediumPortraitConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }

        WindowWidthSizeClass.EXPANDED -> {
            ExpandedPortraitConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }
    }
}

@Composable
fun CompactLandscapeConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))

        OutlinedCard(
            modifier = Modifier.fillMaxHeight(0.8f)
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

        Spacer(modifier = Modifier.weight(6f))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.weight(10f))

            Text(
                text = stringResource(id = R.string.connection1_desc),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = stringResource(id = R.string.finish_warning),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            Spacer(modifier = Modifier.weight(4f))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
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

            Spacer(modifier = Modifier.weight(10f))
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun MediumLandscapeConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))

        OutlinedCard(
            modifier = Modifier.fillMaxHeight(0.6f)
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

        Spacer(modifier = Modifier.weight(6f))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(10f))

            Text(
                text = stringResource(id = R.string.connection1_desc),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = stringResource(id = R.string.finish_warning),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(0.6f)
            )

            Spacer(modifier = Modifier.weight(4f))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Button(
                    onClick = {
                        updateOnBoardingCompleted(true)
                        navigateToHome()
                        registerLogsDownloadWorkRequest(context)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.finish),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(10f))
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun ExpandedLandscapeConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))

        OutlinedCard(
            modifier = Modifier.fillMaxHeight(0.5f)
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

        Spacer(modifier = Modifier.weight(6f))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(10f))

            Text(
                text = stringResource(id = R.string.connection1_desc),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(0.4f)
            )

            Spacer(modifier = Modifier.weight(2f))

            Text(
                text = stringResource(id = R.string.finish_warning),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(0.4f)
            )

            Spacer(modifier = Modifier.weight(4f))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(0.4f)
            ) {
                Button(
                    onClick = {
                        updateOnBoardingCompleted(true)
                        navigateToHome()
                        registerLogsDownloadWorkRequest(context)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.finish),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.weight(10f))
        }

        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun LandscapeConnection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowHeightSizeClass) {
        WindowHeightSizeClass.COMPACT -> {
            CompactLandscapeConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }

        WindowHeightSizeClass.MEDIUM -> {
            MediumLandscapeConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }

        WindowHeightSizeClass.EXPANDED -> {
            ExpandedLandscapeConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }
    }
}

@Composable
fun Connection2Screen(
    getQRCode: (Int, Int) -> ImageBitmap,
    updateOnBoardingCompleted: (boolean: Boolean) -> Unit,
    navigateToHome: () -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenOrientation = LocalContext.current.resources.configuration.orientation

    when (screenOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }

        else -> {
            PortraitConnection2Screen(
                getQRCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                modifier
            )
        }
    }
}

@MultiDevicePreview
@Composable
private fun Connection2ScreenPreviewPhone() {
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
    AppTheme {
        AppBackground {
            Connection2Screen(
                getQRCode = { _, _ ->
                    imageBitMap
                },
                updateOnBoardingCompleted = { _ -> },
                navigateToHome = {},
                registerLogsDownloadWorkRequest = { _ -> },
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}