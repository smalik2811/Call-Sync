package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.component.CalculatorButton
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.GifImage
import com.yangian.callsync.core.designsystem.isPermissionGranted
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun ColumnScope.CompactUnlockScreen() {

    Text(
        text = stringResource(R.string.unlock_step_description),
        style = MaterialTheme.typography.headlineSmall,
    )

    Spacer(
        modifier = Modifier.height(dimensionResource(R.dimen.padding_medium))
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small)
            )
    ) {

        CalculatorButton(
            text = stringResource(R.string.equal_sign),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
            backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            contentColor = MaterialTheme.colorScheme.onPrimary,
        )

        CalculatorButton(
            text = stringResource(R.string.number_1),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
        )

        CalculatorButton(
            text = stringResource(R.string.number_8),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
        )

        CalculatorButton(
            text = stringResource(R.string.number_5),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
        )

        CalculatorButton(
            text = stringResource(R.string.number_6),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
        )

        CalculatorButton(
            text = stringResource(R.string.number_8),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
        )

        CalculatorButton(
            text = stringResource(R.string.equal_sign),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(ratio = 1f, true),
            backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            contentColor = MaterialTheme.colorScheme.onPrimary,
        )
    }

    Spacer(
        modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large))
    )

    GifImage(
        imageID = R.drawable.num_sum_unlock_tutorial,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.weight(1f)
    )
}

@Composable
fun ExpandedUnlockScreen() {

    GifImage(
        imageID = R.drawable.num_sum_unlock_tutorial,
        contentScale = ContentScale.Inside,
        modifier = Modifier.fillMaxHeight()
    )

    Spacer(
        modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_large))
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_large)),
    ) {
        Text(
            text = stringResource(R.string.unlock_step_description),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    end = dimensionResource(R.dimen.padding_small)
                )
        ) {

            CalculatorButton(
                text = stringResource(R.string.equal_sign),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )

            CalculatorButton(
                text = stringResource(R.string.number_1),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_8),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_5),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_6),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_8),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.equal_sign),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                contentColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun UnlockScreen(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .windowInsetsPadding(WindowInsets.safeContent)
            ) {
                ExpandedUnlockScreen()
            }
        }

        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = modifier
            ) {
                CompactUnlockScreen()
            }
        }
    }

    if (!context.isPermissionGranted(Manifest.permission.CAMERA)) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            1
        )
    }
}

@Preview
@Composable
private fun CompactUnlockScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                UnlockScreen(
                    PaddingValues(0.dp),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun ExpandedUnlockScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                UnlockScreen(
                    PaddingValues(0.dp),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}