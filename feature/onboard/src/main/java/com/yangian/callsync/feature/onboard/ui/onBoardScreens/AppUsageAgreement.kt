package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun AppUsageAgreement(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        val privacyPolicyUrl = stringResource(R.string.privacy_policy_document_link)
        val termsOfServiceUrl = stringResource(R.string.terms_of_service_document_link)
        val eulaUrl = stringResource(R.string.eula_document_link)

        val appUsageAgreementText = buildAnnotatedString {
            append("Please review our")
            append(stringResource(R.string.space_string))

            // Privacy Policy
            pushStringAnnotation(
                tag = "privacyPolicyTag",
                annotation = privacyPolicyUrl
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Privacy Policy")
            }
            pop()
            append(", ")

            // Terms of Services
            pushStringAnnotation(
                tag = "termsOfServiceTag",
                annotation = termsOfServiceUrl
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Terms of Services")
            }
            pop()
            append(", and ")

            // EULA
            pushStringAnnotation(
                tag = "eulaTag",
                annotation = eulaUrl
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("EULA")
            }
            pop()

            append(" before proceeding. By clicking \"Agree and Proceed,\" you indicate your acceptance of these agreements.")
        }

        Text(
            text = appUsageAgreementText,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun AppUsageAgreementPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                AppUsageAgreement()
            }
        }
    }
}