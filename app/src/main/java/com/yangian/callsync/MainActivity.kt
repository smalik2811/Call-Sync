package com.yangian.callsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.common.api.internal.LifecycleCallback
import com.yangian.callsync.core.data.util.NetworkMonitor
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.core.workmanager.LogsDownloadWorker
import com.yangian.callsync.ui.CallSyncApp
import com.yangian.callsync.ui.rememberCallSyncAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
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
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().setKeepOnScreenCondition {
            !mainViewModel.isSplashVisible.value
        }

        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        MobileAds.initialize(this)

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

        lifecycleScope.launch { ->
            val isOnboardingDone = userPreferences.getOnboardingDone().first()
            if (isOnboardingDone) {
                val existingWorkPolicy = userPreferences.getWorkerRetryPolicy().first()

                val workerConstraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val workRequest = PeriodicWorkRequestBuilder<LogsDownloadWorker>(
                    repeatInterval = existingWorkPolicy,
                    repeatIntervalTimeUnit = TimeUnit.MINUTES,
                ).setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    2,
                    TimeUnit.MINUTES,
                ).setConstraints(
                    workerConstraints
                ).build()

                val workManager = WorkManager.getInstance(this@MainActivity)
                workManager.enqueueUniquePeriodicWork(
                    "LOGS_DOWNLOAD_WORKER",
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest
                )
            }
        }
    }
}