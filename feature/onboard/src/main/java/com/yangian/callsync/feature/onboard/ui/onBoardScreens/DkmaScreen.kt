package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.CustomAlertDialog
import com.yangian.callsync.core.designsystem.component.DkmaView
import com.yangian.callsync.core.designsystem.icon.ErrorIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.network.model.DkmaManufacturer
import com.yangian.callsync.feature.onboard.DkmaUiState
import com.yangian.callsync.feature.onboard.R
import kotlin.system.exitProcess

@Composable
fun DkmaScreen(
    dkmaUiState: DkmaUiState,
    isIssueVisible: Boolean,
    isSolutionVisible: Boolean,
    alterIssueVisibility: () -> Unit,
    alterSolutionVisibility: () -> Unit,
    retryDkmaLoading: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        when (dkmaUiState) {
            is DkmaUiState.Success -> {
                DkmaView(
                    dkmaManufacturer = dkmaUiState.dkmaManufacturer,
                    isIssueVisible,
                    isSolutionVisible,
                    alterIssueVisibility,
                    alterSolutionVisibility,
                    modifier.weight(1f)
                )
            }

            is DkmaUiState.Error -> {
                DkmaErrorScreen(retryDkmaLoading)
            }

            is DkmaUiState.Loading -> {
                DkmaLoadingScreen(modifier = modifier)
            }
        }
    }
}

@Composable
private fun DkmaLoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun DkmaLoadingScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            DkmaLoadingScreen(
                Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun DkmaErrorScreen(
    retryDkmaLoading: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomAlertDialog(
        onNegativeButtonClick = {
            exitProcess(0)
        },
        onPositiveButtonClick = retryDkmaLoading,
        onDismissRequest = {},
        dialogTitle = stringResource(R.string.something_went_wrong),
        dialogText = stringResource(R.string.dkma_error_dialog_message),
        positiveButtonText = stringResource(R.string.retry),
        negativeButtonText = stringResource(R.string.exit_app),
        iconContentDescriptionText = stringResource(R.string.error_icon),
        icon = ErrorIcon,
        modifier = modifier
    )
}

@Preview
@Composable
private fun DkmaErrorScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            DkmaErrorScreen(
                retryDkmaLoading = {}
            )
        }
    }
}

@Preview
@Composable
private fun DkmaPreviewLoading() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            CallSyncAppBackground {
                DkmaScreen(
                    dkmaUiState = DkmaUiState.Loading,
                    isIssueVisible = false,
                    isSolutionVisible = false,
                    alterIssueVisibility = {},
                    alterSolutionVisibility = {},
                    retryDkmaLoading = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun DkmaPreviewError() {
    CallSyncAppTheme {
        CallSyncAppTheme {
            CallSyncAppBackground {
                DkmaScreen(
                    dkmaUiState = DkmaUiState.Error,
                    isIssueVisible = false,
                    isSolutionVisible = false,
                    alterIssueVisibility = {},
                    alterSolutionVisibility = {},
                    retryDkmaLoading = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun DkmaPreviewSuccess() {
    val dummyData = DkmaManufacturer(
        explanation = stringResource(R.string.dkma_dummy_explanation),
        user_solution = stringResource(R.string.dkma_dummy_user_solution),
    )
    CallSyncAppTheme {
        CallSyncAppBackground {
            CallSyncAppBackground {
                var issueVisibility by remember { mutableStateOf(false) }
                var solutionVisibility by remember { mutableStateOf(false) }
                DkmaScreen(
                    dkmaUiState = DkmaUiState.Success(dummyData),
                    isIssueVisible = issueVisibility,
                    isSolutionVisible = solutionVisibility,
                    alterIssueVisibility = { issueVisibility = !issueVisibility },
                    alterSolutionVisibility = { solutionVisibility = !solutionVisibility },
                    modifier = Modifier.fillMaxSize(),
                    retryDkmaLoading = {}
                )
            }
        }
    }
}