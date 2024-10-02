package com.yangian.callsync.feature.home.ui

import android.content.Context
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.yangian.callsync.core.designsystem.component.AdMobBanner
import com.yangian.callsync.core.designsystem.component.CustomAlertDialog
import com.yangian.callsync.core.designsystem.component.scrollbar.DraggableScrollbar
import com.yangian.callsync.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.yangian.callsync.core.designsystem.component.scrollbar.scrollbarState
import com.yangian.callsync.core.designsystem.icon.LogoutIcon
import com.yangian.callsync.core.designsystem.icon.MoreVertIcon
import com.yangian.callsync.core.designsystem.icon.RefreshIcon
import com.yangian.callsync.core.ui.CallFeedUiState
import com.yangian.callsync.core.ui.callFeed
import com.yangian.callsync.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isMenuVisible: Boolean,
    isSignOutDialogVisible: Boolean,
    focussedCallResourceId: Long,
    feedState: CallFeedUiState,
    snackBarHostState: SnackbarHostState,
    showMenu: () -> Unit,
    hideMenu: () -> Unit,
    showSignOutDialog: () -> Unit,
    hideSignOutDialog: () -> Unit,
    updateFocussedCallResource: (id: Long) -> Unit,
    downloadLogs: (context: Context) -> Unit,
    navigateToOnboarding: () -> Unit,
    signOut: (context: Context, navigateToOnboarding: () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.call_sync),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                actions = {
                    IconButton(
                        onClick = showMenu
                    ) {
                        Icon(imageVector = MoreVertIcon, stringResource(R.string.menu))
                    }

                    DropdownMenu(
                        expanded = isMenuVisible,
                        onDismissRequest = hideMenu,
                        modifier = Modifier
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.sign_out)) },
                            onClick = showSignOutDialog,
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    downloadLogs(context)
                }
            ) {
                Icon(
                    imageVector = RefreshIcon,
                    contentDescription = stringResource(R.string.refresh_logs)
                )
            }
        },
        bottomBar = {
            AdMobBanner(
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
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
                    onCallResourceItemClick = updateFocussedCallResource,
                )
            }

            val itemsAvailable = when (feedState) {
                CallFeedUiState.Loading -> 0
                is CallFeedUiState.Success -> feedState.feed.size
            }

            val scrollbarState = scrollableState.scrollbarState(
                itemsAvailable = itemsAvailable
            )

            scrollableState.DraggableScrollbar(
                modifier = Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = dimensionResource(R.dimen.padding_tiny))
                    .align(Alignment.CenterEnd),
                state = scrollbarState,
                orientation = Orientation.Vertical,
                onThumbMoved = scrollableState.rememberDraggableScroller(
                    itemsAvailable = itemsAvailable
                )
            )
        }
    }

    AnimatedVisibility(isSignOutDialogVisible) {
        CustomAlertDialog(
            onDismissRequest = hideSignOutDialog,
            onNegativeButtonClick = hideSignOutDialog,
            onPositiveButtonClick = {
                signOut(
                    context,
                    navigateToOnboarding
                )
            },
            dialogTitle = stringResource(R.string.sign_out_dialog_title),
            dialogText = stringResource(R.string.sign_out_dialog_description),
            icon = LogoutIcon,
            positiveButtonText = stringResource(R.string.yes),
            negativeButtonText = stringResource(R.string.cancel),
            iconContentDescriptionText = stringResource(R.string.logout)
        )
    }
}