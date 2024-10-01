package com.yangian.callsync.feature.home.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yangian.callsync.feature.home.ui.HomeScreen

const val HOME_ROUTE = "home"
fun NavGraphBuilder.homeScreen(
    navigateToOnboarding: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToOnboarding = navigateToOnboarding
        )
    }
}