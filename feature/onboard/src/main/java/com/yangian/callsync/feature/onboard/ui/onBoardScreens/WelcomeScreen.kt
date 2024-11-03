package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun CompactWelcomeScreen() {
    // App icon
    Box(
        modifier = Modifier.shadow(
            elevation = dimensionResource(R.dimen.elevation_level_2),
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_extra_large))
        )
    ) {
        Image(
            painter = painterResource(R.mipmap.launcher_background),
            contentDescription = stringResource(R.string.call_sync_app_logo),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        dimensionResource(
                            R.dimen.corner_radius_extra_large
                        )
                    )
                )
        )

        Image(
            painter = painterResource(R.mipmap.call_sync_launcher_foreground),
            contentDescription = stringResource(R.string.call_sync_app_logo),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        dimensionResource(
                            R.dimen.corner_radius_extra_large
                        )
                    )
                )
        )
    }

    Spacer(
        modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large))
    )

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(R.string.call_sync_name))
            }
            append(stringResource(R.string.space_string))
            append(stringResource(R.string.welcome_description))
        },
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ExpandedWelcomeScreen() {
    // App icon
    Box(
        modifier = Modifier.shadow(
            elevation = dimensionResource(R.dimen.elevation_level_2),
            shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius_extra_large))
        )
    ) {
        Image(
            painter = painterResource(R.mipmap.launcher_background),
            contentDescription = stringResource(R.string.call_sync_app_logo),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        dimensionResource(
                            R.dimen.corner_radius_extra_large
                        )
                    )
                )
        )

        Image(
            painter = painterResource(R.mipmap.call_sync_launcher_foreground),
            contentDescription = stringResource(R.string.call_sync_app_logo),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        dimensionResource(
                            R.dimen.corner_radius_extra_large
                        )
                    )
                )
        )
    }

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(R.string.call_sync_name))
            }
            append(stringResource(R.string.space_string))
            append(stringResource(R.string.welcome_description))
        },
        style = MaterialTheme.typography.headlineSmall.copy(lineBreak = LineBreak.Paragraph),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(0.4f)
    )
}

@Composable
fun WelcomeScreen(
    createFirebaseAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    // Create new Firebase Account if not exist
    createFirebaseAccount()

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
            ) {
                ExpandedWelcomeScreen()
            }
        }

        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                CompactWelcomeScreen()
            }
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenColumnPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                WelcomeScreen(
                    {}
                )
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun WelcomeScreenRowPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                WelcomeScreen(
                    {}
                )
            }
        }
    }
}