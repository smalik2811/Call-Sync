package com.yangian.callsync.ui

import androidx.compose.runtime.Composable
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.navigation.CallSyncAppNavHost

@Composable
fun CallSyncApp(
    appState: CallSyncAppState = rememberCallSyncAppState(),
    startDestination: String,
    firebaseAnalytics: FirebaseAnalytics
) {
    CallSyncAppNavHost(
        appState = appState,
        startDestination = startDestination,
        firebaseAnalytics = firebaseAnalytics
    )
}