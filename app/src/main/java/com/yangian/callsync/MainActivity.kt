package com.yangian.callsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.yangian.callsync.core.data.util.NetworkMonitor
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.ui.CallSyncApp
import com.yangian.callsync.ui.rememberCallSyncAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN) {
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "app_open")
        }

        installSplashScreen().setKeepOnScreenCondition {
            !mainViewModel.isSplashVisible.value
        }

        enableEdgeToEdge()
        setContent {
            val startDestination by mainViewModel.startDestination
            val appState = rememberCallSyncAppState(
                networkMonitor = networkMonitor
            )
            CallSyncAppTheme {
                CallSyncAppBackground {
                    CallSyncApp(
                        appState = appState,
                        startDestination = startDestination,
                        firebaseAnalytics = firebaseAnalytics
                    )
                }
            }
        }
    }

}