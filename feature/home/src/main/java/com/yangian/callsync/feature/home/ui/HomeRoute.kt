package com.yangian.callsync.feature.home.ui

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.core.designsystem.component.AdMobBanner
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.CustomAlertDialog
import com.yangian.callsync.core.designsystem.component.scrollbar.DraggableScrollbar
import com.yangian.callsync.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.yangian.callsync.core.designsystem.component.scrollbar.scrollbarState
import com.yangian.callsync.core.designsystem.icon.LogoutIcon
import com.yangian.callsync.core.designsystem.icon.MoreVertIcon
import com.yangian.callsync.core.designsystem.icon.RefreshIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.ui.CallFeedUiState
import com.yangian.callsync.core.ui.callFeed
import com.yangian.callsync.feature.home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    activity: Activity,
    firebaseAnalytics: FirebaseAnalytics?,
    navigateToOnboarding: () -> Unit
) {

    val feedState by homeViewModel.feedState.collectAsState()
    val focussedCallResourceId by homeViewModel.focussedCallResourceId.collectAsState()
    var isSignoutDialogVisible by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Call Sync",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            menuExpanded = !menuExpanded
                        }
                    ) {
                        Icon(imageVector = MoreVertIcon, "Menu")
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        offset = DpOffset((-12).dp, 0.dp),
                        modifier = Modifier
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sign out") },
                            onClick = { isSignoutDialogVisible = true },
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = homeViewModel.snackBarHostState)
        },
        floatingActionButton = {
            val coroutineScope = rememberCoroutineScope()
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        homeViewModel.downloadLogs()
                    }
                }
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
            val scrollableState = rememberLazyListState()
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                state = scrollableState,
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

            val itemsAvailable = when (feedState) {
                CallFeedUiState.Loading -> 0
                is CallFeedUiState.Success -> (feedState as CallFeedUiState.Success).feed.size
            }

            val scrollbarState = scrollableState.scrollbarState(
                itemsAvailable = itemsAvailable
            )

            scrollableState.DraggableScrollbar(
                modifier = Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = 2.dp)
                    .align(Alignment.CenterEnd),
                state = scrollbarState,
                orientation = Orientation.Vertical,
                onThumbMoved = scrollableState.rememberDraggableScroller(
                    itemsAvailable = itemsAvailable
                )
            )
        }
    }

    AnimatedVisibility(isSignoutDialogVisible) {
        CustomAlertDialog(
            onDismissRequest = {
                isSignoutDialogVisible = false
            },
            onConfirmation = {
                homeViewModel.signout(
                    context = context,
                    navigateToOnboarding = navigateToOnboarding
                )
            },
            dialogTitle = "Are you sure you want to sign out?",
            dialogText = "Once signed out, you will no longer receive latest call logs and the existing logs will be deleted.",
            icon = LogoutIcon
        )
    }
}

@Preview
@Composable
private fun HomeRoutePreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            HomeRoute(
                activity = LocalContext.current as Activity,
                firebaseAnalytics = null,
                navigateToOnboarding = {}
            )
        }
    }
}