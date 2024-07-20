package com.yangian.callsync.feature.home.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yangian.callsync.core.designsystem.component.AdMobBanner
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.icon.MoreVertIcon
import com.yangian.callsync.core.designsystem.icon.RefreshIcon
import com.yangian.callsync.core.designsystem.icon.SearchIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.ui.callFeed
import com.yangian.callsync.feature.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    activity: Activity
) {

    val feedState by homeViewModel.feedState.collectAsState()
    val focussedCallResourceId by homeViewModel.focussedCallResourceId.collectAsState()

    val isSearching = false
    Scaffold(
        topBar = {
            SearchBar(
                query = "",
                onQueryChange = {},
                onSearch = {},
                active = isSearching,
                onActiveChange = {},
                placeholder = {
                    Text("Search Logs")
                },
                leadingIcon = {
                    Icon(
                        imageVector = SearchIcon,
                        contentDescription = "Search Logs"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = MoreVertIcon,
                        contentDescription = "Menu"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = if (isSearching) 0.dp else 12.dp,
                        end = if (isSearching) 0.dp else 12.dp,
                    )
            ) {
                Text("Not Searching Yet")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = homeViewModel.snackBarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = homeViewModel::downloadLogs
            ) {
                Icon(
                    imageVector = RefreshIcon,
                    contentDescription = "Refresh Logs"
                )
            }
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
            HomeRoute(activity = LocalContext.current as Activity)
        }
    }
}