package com.yangian.callsync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yangian.callsync.R
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.icon.CloudOffIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme

@Composable
fun OfflineScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                dimensionResource(
                    com.yangian.callsync.core.ui.R.dimen.padding_medium
                )
            ),
    ) {
        Icon(
            CloudOffIcon,
            stringResource(R.string.no_internet_connection),
            modifier = Modifier
                .size(dimensionResource(com.yangian.callsync.core.ui.R.dimen.icon_size_large))
        )

        Spacer(
            modifier = Modifier.height(
                dimensionResource(
                    com.yangian.callsync.core.ui.R.dimen.padding_medium
                )
            )
        )

        Text("You are offline. Check your connection.")
    }
}

@Preview
@Composable
private fun OfflineScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            OfflineScreen()
        }
    }
}