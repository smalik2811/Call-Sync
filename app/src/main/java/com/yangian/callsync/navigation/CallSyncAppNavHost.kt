package com.yangian.callsync.navigation

import android.app.Activity
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
    activity: Activity
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        homeScreen(activity = activity)

        onBoardScreen(
            navigateToHome = {
                appState.navigateToDestination(CallSyncDestination.Home)
            }
        )
    }
}