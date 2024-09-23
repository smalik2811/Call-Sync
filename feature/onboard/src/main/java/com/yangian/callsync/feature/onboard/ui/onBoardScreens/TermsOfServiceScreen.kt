package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun TermsOfServiceScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Text(
            text = stringResource(R.string.terms_of_services),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

@Preview
@Composable
private fun TermsOfServiceScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            TermsOfServiceScreen()
        }
    }
}