package com.yangian.callsync.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yangian.callsync.core.data.util.NetworkMonitor
import com.yangian.callsync.navigation.CallSyncDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberCallSyncAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor
): CallSyncAppState {
    return remember(navController) {
        CallSyncAppState(
            navController,
            coroutineScope,
            networkMonitor
        )
    }

}

@Stable
class CallSyncAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {

    private val callSyncDestinations: List<CallSyncDestination> = listOf(
        CallSyncDestination.Home,
        CallSyncDestination.OnBoard
    )

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun navigateToDestination(destination: CallSyncDestination) {
        when (destination) {
            CallSyncDestination.Home -> navController.navigate(CallSyncDestination.Home.route)
            CallSyncDestination.OnBoard -> navController.navigate(CallSyncDestination.OnBoard.route)
        }
    }

}