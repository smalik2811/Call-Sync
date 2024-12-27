package com.yangian.callsync.feature.home.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.core.model.CallResource
import com.yangian.callsync.core.ui.CallFeedUiState
import com.yangian.callsync.core.ui.CallResourcePreviewParameterProvider
import com.yangian.callsync.feature.home.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit
) {
    val feedState by homeViewModel.feedState.collectAsState()
    val focussedCallResourceId by homeViewModel.focussedCallResourceId.collectAsState()
    val isSignOutDialogVisible by homeViewModel.isSignOutDialogVisible.collectAsState()
    val isMenuVisible by homeViewModel.isMenuExpanded.collectAsState()

    HomeScreen(
        isMenuVisible,
        isSignOutDialogVisible,
        focussedCallResourceId,
        feedState,
        homeViewModel.snackBarHostState,
        homeViewModel::showMenu,
        homeViewModel::hideMenu,
        homeViewModel::showSignOutDialog,
        homeViewModel::hideSignOutDialog,
        homeViewModel::updateFocussedCallResource,
        homeViewModel::downloadLogs,
        navigateToOnboarding,
        homeViewModel::signout,
        modifier = modifier
    )

}

@Preview(device = "id:pixel_tablet")
@Composable
private fun HomeRoutePreview(
    @PreviewParameter(CallResourcePreviewParameterProvider::class) callResourceList: List<CallResource>
) {
    AppTheme {
        AppBackground {
            HomeScreen(
                isMenuVisible = false,
                isSignOutDialogVisible = false,
                focussedCallResourceId = 0L,
                feedState = CallFeedUiState.Success(
                    feed = callResourceList
                ),
                snackBarHostState = SnackbarHostState(),
                showMenu = {},
                hideMenu = {},
                showSignOutDialog = {},
                hideSignOutDialog = {},
                updateFocussedCallResource = { },
                downloadLogs = {},
                navigateToOnboarding = {},
                signOut = { _, _ -> },
                modifier = Modifier
            )
        }
    }
}