package com.yangian.callsync.feature.home.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.core.designsystem.component.AdMobBanner
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.ui.callFeed
import com.yangian.callsync.feature.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    activity: Activity,
    firebaseAnalytics: FirebaseAnalytics?
) {

    val feedState by homeViewModel.feedState.collectAsState()
    val focussedCallResourceId by homeViewModel.focussedCallResourceId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Call Sync",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = homeViewModel.snackBarHostState)
        },
        bottomBar = {
            AdMobBanner(
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                callFeed(
                    feedState = feedState,
                    modifier = Modifier.fillMaxSize(),
                    focussedCallResourceId = focussedCallResourceId,
                    onCallResourceItemClick = homeViewModel::updateFocussedStateFlow,
                    activity = activity
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeRoutePreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            HomeRoute(
                activity = LocalContext.current as Activity,
                firebaseAnalytics = null
            )
        }
    }
}