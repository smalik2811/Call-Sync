package com.yangian.callsync.navigation

sealed class CallSyncDestination(
    val route: String
) {
    data object Home: CallSyncDestination(
        route = "home"
    )

    data object OnBoard: CallSyncDestination(
        route = "onboard"
    )
}