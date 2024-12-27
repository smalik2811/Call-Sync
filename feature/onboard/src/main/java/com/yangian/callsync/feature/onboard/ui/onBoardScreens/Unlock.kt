package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.MultiDevicePreview
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.component.CalculatorButton
import com.yangian.callsync.core.designsystem.component.GifImage
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun CompactPortraitUnlockScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
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
}

@Composable
fun MediumPortraitUnlockScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
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
}

@Composable
fun ExpandedPortraitUnlockScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
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
}

@Composable
fun PortraitUnlockScreen(
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            CompactPortraitUnlockScreen(
                modifier
            )
        }

        WindowWidthSizeClass.MEDIUM -> {
            MediumPortraitUnlockScreen(
                modifier
            )
        }

        WindowWidthSizeClass.EXPANDED -> {
            ExpandedPortraitUnlockScreen(
                modifier
            )
        }
    }
}

@Composable
fun CompactLandscapeUnlockScreen(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        GifImage(
            imageID = R.drawable.num_sum_unlock_tutorial,
            contentScale = ContentScale.Inside,
//        modifier = Modifier.fillMaxHeight()
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
}

@Composable
fun MediumLandscapeUnlockScreen(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        GifImage(
            imageID = R.drawable.num_sum_unlock_tutorial,
            contentScale = ContentScale.Inside,
//        modifier = Modifier.fillMaxHeight()
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
}

@Composable
fun ExpandedLandscapeUnlockScreen(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        GifImage(
            imageID = R.drawable.num_sum_unlock_tutorial,
            contentScale = ContentScale.Inside,
//        modifier = Modifier.fillMaxHeight()
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
}

@Composable
fun LandscapeUnlockScreen(
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowHeightSizeClass) {
        WindowHeightSizeClass.COMPACT -> {
            CompactLandscapeUnlockScreen(
                modifier
            )
        }

        WindowHeightSizeClass.MEDIUM -> {
            MediumLandscapeUnlockScreen(
                modifier
            )
        }

        WindowHeightSizeClass.EXPANDED -> {
            ExpandedLandscapeUnlockScreen(
                modifier
            )
        }
    }
}

@Composable
fun UnlockScreen(modifier: Modifier = Modifier) {
    val screenOrientation = LocalContext.current.resources.configuration.orientation

    when (screenOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeUnlockScreen(
                modifier
            )
        }

        else -> {
            PortraitUnlockScreen(
                modifier
            )
        }
    }
}

@MultiDevicePreview
@Composable
private fun UnlockScreenPreviewPhone() {
    AppTheme {
        AppBackground {
            UnlockScreen(Modifier.fillMaxSize())
        }
    }
}