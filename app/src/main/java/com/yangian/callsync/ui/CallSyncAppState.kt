package com.yangian.callsync.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yangian.callsync.navigation.CallSyncDestination

@Composable
fun rememberCallSyncAppState(
    navController: NavHostController = rememberNavController(),
): CallSyncAppState {
    return remember(navController) {
        CallSyncAppState(navController)
    }

}

@Stable
class CallSyncAppState(
    val navController: NavHostController,
) {
    
    private val callSyncDestinations: List<CallSyncDestination> = listOf(
        CallSyncDestination.Home,
        CallSyncDestination.OnBoard
    )

    fun navigateToDestination(destination: CallSyncDestination) {
        when (destination) {
            CallSyncDestination.Home -> navController.navigate(CallSyncDestination.Home.route)
            CallSyncDestination.OnBoard -> navController.navigate(CallSyncDestination.OnBoard.route)
        }
    }

}