package com.yangian.callsync.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.navigation.CallSyncAppNavHost

@Composable
fun CallSyncApp(
    appState: CallSyncAppState = rememberCallSyncAppState(),
    startDestination: String,
    activity: Activity,
    firebaseAnalytics: FirebaseAnalytics
) {
    CallSyncAppNavHost(
        appState = appState,
        startDestination = startDestination,
        activity = activity,
        firebaseAnalytics = firebaseAnalytics
    )
}