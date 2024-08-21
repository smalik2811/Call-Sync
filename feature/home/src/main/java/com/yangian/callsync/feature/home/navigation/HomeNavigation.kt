package com.yangian.callsync.feature.home.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.feature.home.ui.HomeRoute

const val HOME_ROUTE = "home"
fun NavGraphBuilder.homeScreen(
    activity: Activity,
    firebaseAnalytics: FirebaseAnalytics
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(
            modifier = Modifier.fillMaxSize(),
            activity = activity,
            firebaseAnalytics = firebaseAnalytics
        )
    }
}