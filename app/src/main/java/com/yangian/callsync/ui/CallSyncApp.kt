package com.yangian.callsync.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yangian.callsync.navigation.CallSyncAppNavHost

@Composable
fun CallSyncApp(
    appState: CallSyncAppState,
    startDestination: String,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    if (isOffline) {
        OfflineScreen(
            windowSizeClass,
            modifier
        )
    } else {
        CallSyncAppNavHost(
            appState = appState,
            startDestination = startDestination,
            modifier = modifier
        )
    }
}