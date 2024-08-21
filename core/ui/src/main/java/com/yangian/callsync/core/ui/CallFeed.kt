package com.yangian.callsync.core.ui

import android.Manifest
import android.app.Activity
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.model.CallResource

fun triggerSMSIntent(callResourceNumber: String, activity: Activity) {
    val uriSMS: Uri = Uri.parse("smsto:$callResourceNumber")
    val smsIntent: Intent = Intent(Intent.ACTION_SENDTO, uriSMS)
    startActivity(activity, smsIntent, null)
}

fun triggerCallIntent(callResourceNumber: String, activity: Activity) {
    val uriCall: Uri = Uri.parse("tel:$callResourceNumber")
    val callIntent: Intent = Intent(Intent.ACTION_CALL, uriCall)

    if (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CALL_PHONE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CALL_PHONE),
            0
        )
    } else {
        startActivity(activity, callIntent, null)
    }
}

fun LazyListScope.callFeed(
    feedState: CallFeedUiState,
    modifier: Modifier = Modifier,
    focussedCallResourceId: Long,
    onCallResourceItemClick: (Long) -> Unit = {},
    activity: Activity
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
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(20.dp)
                    ) {

                        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            Spacer(modifier = Modifier.fillParentMaxHeight(0.1f))

                            Image(
                                painter = painterResource(id = R.drawable.observing),
                                contentDescription = "Loading Data"
                            )
                        } else {
                            Spacer(modifier = Modifier.fillParentMaxHeight(0.2f))
                        }

                        Spacer(modifier = Modifier.fillParentMaxHeight(0.15f))

                        Text(
                            text = "No call history available",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Spacer(modifier = Modifier.fillParentMaxHeight(0.01f))

                        Text(
                            text = "We will automatically update the call history in the background.",
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
                    CallResourceListItem(
                        callResource = callResource,
                        focussedCallResourceId = focussedCallResourceId,
                        onCallResourceItemClick = onCallResourceItemClick,
                        onSMSClick = {
                            triggerSMSIntent(callResource.number, activity)
                        },
                        onCallClick = {
                            triggerCallIntent(callResource.number, activity)
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
    val activity = LocalContext.current as Activity
    CallSyncAppTheme {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {

            callFeed(
                feedState = CallFeedUiState.Loading,
                modifier = Modifier.fillMaxWidth(),
                focussedCallResourceId = -1,
                activity = activity
            )
        }
    }
}