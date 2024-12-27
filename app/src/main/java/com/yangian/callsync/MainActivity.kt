package com.yangian.callsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.android.gms.ads.MobileAds
import com.yangian.callsync.core.data.util.NetworkMonitor
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.ui.CallSyncApp
import com.yangian.callsync.ui.rememberCallSyncAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var userPreferences: UserPreferences

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().setKeepOnScreenCondition {
            !mainViewModel.isSplashVisible.value
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            ),
        )

        setContent {
            val startDestination by mainViewModel.startDestination
            val appState = rememberCallSyncAppState(
                networkMonitor = networkMonitor
            )
            val windowSizeClass = calculateWindowSizeClass(this)
            AppTheme {
                Surface {
                    CallSyncApp(
                        appState = appState,
                        startDestination = startDestination,
                        windowSizeClass = windowSizeClass,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}