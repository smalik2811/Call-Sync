package com.yangian.callsync.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yangian.callsync.feature.home.navigation.homeScreen
import com.yangian.callsync.feature.onboard.navigation.onBoardScreen
import com.yangian.callsync.ui.CallSyncAppState

@Composable
fun CallSyncAppNavHost(
    appState: CallSyncAppState,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        homeScreen(
            navigateToOnboarding = {
                appState.navigateToDestination(CallSyncDestination.OnBoard)
            },
            modifier
        )

        onBoardScreen(
            navigateToHome = {
                appState.navigateToDestination(CallSyncDestination.Home)
            },
            modifier
        )
    }
}