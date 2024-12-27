package com.yangian.callsync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yangian.callsync.R
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.icon.CloudOffIcon
import com.yangian.callsync.core.designsystem.theme.AppTheme

@Composable
fun CompactOfflineScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            CloudOffIcon,
            stringResource(R.string.no_internet_connection),
        )

        Text(
            text = "You are offline.\nCheck your connection.",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ExpandedOfflineScreen(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            CloudOffIcon,
            stringResource(R.string.no_internet_connection),
            modifier = Modifier
                .fillMaxHeight(0.24f)
                .aspectRatio(1f, true)
        )

        Text(
            text = "You are offline.\nCheck your connection.",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(48.dp)
        )
    }
}

@Composable
fun OfflineScreen(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        CompactOfflineScreen(modifier = modifier)
    } else {
        ExpandedOfflineScreen(modifier = modifier)
    }
}

@Preview
@Composable
private fun CompactOfflineScreenPreview() {
    AppTheme {
        AppBackground {
            CompactOfflineScreen(modifier = Modifier)
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun ExpandedOfflineScreenPreview() {
    AppTheme {
        AppBackground {
            ExpandedOfflineScreen(modifier = Modifier)
        }
    }
}