package com.yangian.callsync.feature.onboard.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yangian.callsync.feature.onboard.ui.OnBoardRoute

const val ONBOARD_ROUTE = "onboard"

fun NavGraphBuilder.onBoardScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable(route = ONBOARD_ROUTE) {
        OnBoardRoute(
            navigateToHome = navigateToHome,
            modifier = modifier,
        )
    }
}