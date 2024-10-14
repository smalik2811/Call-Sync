package com.yangian.callsync.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yangian.callsync.navigation.CallSyncAppNavHost

@Composable
fun CallSyncApp(
    appState: CallSyncAppState,
    startDestination: String,
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    if (isOffline) {
        OfflineScreen()
    } else {
        CallSyncAppNavHost(
            appState = appState,
            startDestination = startDestination,
        )
    }
}