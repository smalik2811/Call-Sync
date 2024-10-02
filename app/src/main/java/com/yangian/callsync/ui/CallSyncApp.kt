package com.yangian.callsync.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.navigation.CallSyncAppNavHost

@Composable
fun CallSyncApp(
    appState: CallSyncAppState,
    startDestination: String,
    firebaseAnalytics: FirebaseAnalytics
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    if (isOffline) {
        OfflineScreen()
    } else {
        CallSyncAppNavHost(
            appState = appState,
            startDestination = startDestination,
            firebaseAnalytics = firebaseAnalytics
        )
    }
}