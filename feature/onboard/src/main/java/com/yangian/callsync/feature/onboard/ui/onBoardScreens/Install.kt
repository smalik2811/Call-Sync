package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.MultiDevicePreview
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.component.ForeignAppMiniAppInstallCard
import com.yangian.callsync.core.designsystem.component.HostAppMiniAppInstallCard
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun CompactPortraitInstallScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))
        HostAppMiniAppInstallCard()
        Spacer(modifier = Modifier.weight(2f))
        ForeignAppMiniAppInstallCard()
        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun MediumPortraitInstallScreen(
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))
        HostAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleMedium,
            launcherIconWidthFactor = 0.2f,
            globalPadding = R.dimen.padding_large,
            textPadding = R.dimen.padding_medium,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Spacer(modifier = Modifier.weight(2f))
        ForeignAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleMedium,
            launcherIconFactor = 0.2f,
            globalPadding = R.dimen.padding_large,
            textPadding = R.dimen.padding_medium,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun ExpandedPortraitInstallScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))
        HostAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleLarge,
            launcherIconWidthFactor = 0.2f,
            globalPadding = R.dimen.padding_extra_large,
            textPadding = R.dimen.padding_large,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Spacer(modifier = Modifier.weight(2f))
        ForeignAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleLarge,
            launcherIconFactor = 0.2f,
            globalPadding = R.dimen.padding_extra_large,
            textPadding = R.dimen.padding_large,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun PortraitInstallScreen(
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            CompactPortraitInstallScreen(
                modifier
            )
        }

        WindowWidthSizeClass.MEDIUM -> {
            MediumPortraitInstallScreen(
                modifier
            )
        }

        WindowWidthSizeClass.EXPANDED -> {
            ExpandedPortraitInstallScreen(
                modifier
            )
        }
    }
}

@Composable
fun CompactLandscapeInstallScreen(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        HostAppMiniAppInstallCard(modifier = Modifier.weight(10f))
        Spacer(modifier = Modifier.weight(1f))
        ForeignAppMiniAppInstallCard(modifier = Modifier.weight(10f))
    }
}

@Composable
fun MediumLandscapeInstallScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))
        HostAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleMedium,
            launcherIconWidthFactor = 0.2f,
            globalPadding = R.dimen.padding_large,
            textPadding = R.dimen.padding_medium,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Spacer(modifier = Modifier.weight(2f))
        ForeignAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleMedium,
            launcherIconFactor = 0.2f,
            globalPadding = R.dimen.padding_large,
            textPadding = R.dimen.padding_medium,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun ExpandedLandscapeInstallScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(10f))
        HostAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleLarge,
            launcherIconWidthFactor = 0.2f,
            globalPadding = R.dimen.padding_extra_large,
            textPadding = R.dimen.padding_large,
            modifier = Modifier.fillMaxWidth(0.4f)
        )
        Spacer(modifier = Modifier.weight(2f))
        ForeignAppMiniAppInstallCard(
            titleStyle = MaterialTheme.typography.headlineLarge,
            descStyle = MaterialTheme.typography.titleLarge,
            launcherIconFactor = 0.2f,
            globalPadding = R.dimen.padding_extra_large,
            textPadding = R.dimen.padding_large,
            modifier = Modifier.fillMaxWidth(0.4f)
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}

@Composable
fun LandscapeInstallScreen(
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    when (windowSizeClass.windowHeightSizeClass) {
        WindowHeightSizeClass.COMPACT -> {
            CompactLandscapeInstallScreen(
                modifier
            )
        }

        WindowHeightSizeClass.MEDIUM -> {
            MediumLandscapeInstallScreen(
                modifier
            )
        }

        WindowHeightSizeClass.EXPANDED -> {
            ExpandedLandscapeInstallScreen(
                modifier
            )
        }
    }
}

@Composable
fun InstallScreen(
    createFirebaseAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenOrientation = LocalContext.current.resources.configuration.orientation

    when (screenOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeInstallScreen(
                modifier
            )
        }

        else -> {
            PortraitInstallScreen(
                modifier
            )
        }
    }

    createFirebaseAccount()
}

@MultiDevicePreview
@Composable
private fun InstallScreenPreviewPhone() {
    AppTheme {
        AppBackground {
            InstallScreen(
                {},
                Modifier.fillMaxSize()
            )
        }
    }
}