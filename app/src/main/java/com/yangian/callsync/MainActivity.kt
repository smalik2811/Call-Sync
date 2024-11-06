package com.yangian.callsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.yangian.callsync.core.data.util.NetworkMonitor
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.workmanager.LogsDownloadWorker
import com.yangian.callsync.ui.CallSyncApp
import com.yangian.callsync.ui.rememberCallSyncAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.first
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
        enableEdgeToEdge()

        setContent {
            val startDestination by mainViewModel.startDestination
            val appState = rememberCallSyncAppState(
                networkMonitor = networkMonitor
            )
            val windowSizeClass = calculateWindowSizeClass(this)
            CallSyncAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .safeDrawingPadding(),
                ) {

                    CallSyncApp(
                        appState = appState,
                        startDestination = startDestination,
                        windowSizeClass = windowSizeClass,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }

        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60 * 60 * 24 // 1 Day
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnSuccessListener {
                val workerRetryPolicy = remoteConfig.getLong("WorkerRetryPolicy")
                lifecycleScope.launch {
                    val existingUserPreferences = userPreferences.getWorkerRetryPolicy().first()
                    if (existingUserPreferences != workerRetryPolicy) {
                        userPreferences.setWorkerRetryPolicy(workerRetryPolicy)
                        scheduleWorker(workerRetryPolicy)
                    }
                }
            }
    }

    private fun scheduleWorker(retryPolicy: Long) {
        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<LogsDownloadWorker>(
            repeatInterval = retryPolicy,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
        ).setConstraints(workerConstraints)
            .build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueueUniquePeriodicWork(
            "LOGS_DOWNLOAD_WORKER",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

}