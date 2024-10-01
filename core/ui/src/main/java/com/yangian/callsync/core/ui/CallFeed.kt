package com.yangian.callsync.core.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.model.CallResource

fun triggerSMSIntent(callResourceNumber: String, context: Context) {
    val uriSMS: Uri = Uri.parse("smsto:$callResourceNumber")
    val smsIntent = Intent(Intent.ACTION_SENDTO, uriSMS)
    startActivity(context, smsIntent, null)
}

fun triggerCallIntent(callResourceNumber: String, context: Context) {
    val uriCall: Uri = Uri.parse("tel:$callResourceNumber")
    val callIntent = Intent(Intent.ACTION_CALL, uriCall)

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CALL_PHONE),
            0
        )
    } else {
        startActivity(context, callIntent, null)
    }
}

fun LazyListScope.callFeed(
    feedState: CallFeedUiState,
    modifier: Modifier = Modifier,
    focussedCallResourceId: Long,
    onCallResourceItemClick: (Long) -> Unit = {}
) {
    when (feedState) {
        is CallFeedUiState.Loading -> {
            item {
                CircularProgressIndicator()
            }
        }

        is CallFeedUiState.Success -> {
            if (feedState.feed.isEmpty()) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = modifier
                            .fillParentMaxSize()
                            .padding(dimensionResource(R.dimen.padding_large))
                    ) {

                        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            Spacer(modifier = Modifier.fillParentMaxHeight(0.1f))

                            Image(
                                painter = painterResource(id = R.drawable.observing),
                                contentDescription = stringResource(R.string.loading_data)
                            )
                        } else {
                            Spacer(modifier = Modifier.fillParentMaxHeight(0.2f))
                        }

                        Spacer(modifier = Modifier.fillParentMaxHeight(0.15f))

                        Text(
                            text = stringResource(R.string.no_call_history_available),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Spacer(modifier = Modifier.fillParentMaxHeight(0.01f))

                        Text(
                            text = stringResource(R.string.we_will_automatically_update_the_call_history_in_the_background),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            } else {
                items(
                    items = feedState.feed,
                    key = { item: CallResource ->
                        item.id
                    }
                ) { callResource ->
                    val context = LocalContext.current
                    CallResourceListItem(
                        callResource = callResource,
                        focussedCallResourceId = focussedCallResourceId,
                        onCallResourceItemClick = onCallResourceItemClick,
                        onSMSClick = {
                            triggerSMSIntent(callResource.number, context)
                        },
                        onCallClick = {
                            triggerCallIntent(callResource.number, context)
                        },
                    )
                }
            }
        }
    }
}

sealed interface CallFeedUiState {
    data object Loading : CallFeedUiState

    data class Success(
        val feed: List<CallResource>,
    ) : CallFeedUiState
}

@Preview
@Composable
private fun LoadingCallFeedPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {

                callFeed(
                    feedState = CallFeedUiState.Loading,
                    modifier = Modifier.fillMaxWidth(),
                    focussedCallResourceId = -1,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SuccessEmptyCallFeedPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                callFeed(
                    feedState = CallFeedUiState.Success(
                        feed = listOf()
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    focussedCallResourceId = 0
                )
            }
        }
    }
}

@Preview
@Composable
private fun SuccessFilledCallFeedPreview(
    @PreviewParameter(CallResourcePreviewParameterProvider::class) callResourceList: List<CallResource>
) {
    CallSyncAppTheme {
        CallSyncAppBackground {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                callFeed(
                    feedState = CallFeedUiState.Success(
                        feed = callResourceList
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    focussedCallResourceId = 0
                )
            }
        }
    }
}