package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun TermsOfServiceScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        val termsOfServiceText = stringResource(R.string.terms_of_services)
        MarkdownText(
            markdown = termsOfServiceText,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun TermsOfServiceScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                TermsOfServiceScreen()
            }
        }
    }
}