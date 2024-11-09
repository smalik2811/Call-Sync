package com.yangian.callsync.core.workmanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.core.firebase.repository.FirestoreResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

@HiltWorker
class LogsDownloadWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val userPreferences: UserPreferences,
    private val callResourceRepository: CallResourceRepository,
) : CoroutineWorker(context, workerParams) {

    private val TAG = "DOWNLOAD_WORKER"

    private fun scheduleWorker(retryPolicy: Long) {
        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<LogsDownloadWorker>(
            repeatInterval = retryPolicy,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
        ).setConstraints(workerConstraints)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            "LOGS_DOWNLOAD_WORKER",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    override suspend fun doWork(): Result {
        lateinit var result: Result

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60 * 60 * 24 * 3 // 3 Days
        }
        var existingUserPreferences = userPreferences.getWorkerRetryPolicy().first()

        var updateWorkerPolicy = false

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.fetchAndActivate()
            .addOnSuccessListener {
                val workerRetryPolicy = firebaseRemoteConfig.getLong("WorkerRetryPolicy")
                if (existingUserPreferences != workerRetryPolicy) {
                    existingUserPreferences = workerRetryPolicy
                    updateWorkerPolicy = true
                }
            }

        if (updateWorkerPolicy) {
            userPreferences.setWorkerRetryPolicy(existingUserPreferences)
            scheduleWorker(existingUserPreferences)
            return Result.success()
        }

        try {
            // Get current user
            val currentUserId: String = firebaseAuth.currentUser?.uid!!
            val firestoreResult = firestoreRepository.addData(
                currentUserId,
                callResourceRepository,
            )

            result = when (firestoreResult) {
                is FirestoreResult.Success -> Result.success()
                is FirestoreResult.Retry -> Result.retry()
                is FirestoreResult.Failure -> Result.failure()
            }
        } catch (e: Exception) {
            Log.i(TAG, "Kotlin Exception occurred: ${e.message}")
            return Result.retry()
        }
        Log.i(TAG, "Worker Successfully finished.")

        if (result == Result.success()) {
            // Show Notification
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://www.callsync.yangian.com/open-main-activity")
                flags = Intent
                    .FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
            }

            val pendingIntent = PendingIntent
                .getActivity(
                    context, 0, intent, PendingIntent.FLAG_IMMUTABLE
                )

            val notificationId = 1
            val notificationBuilder =
                NotificationCompat.Builder(context, "call_sync_notifications")
                    .setSmallIcon(R.drawable.call_sync_notification)
                    .setContentTitle("Sync Complete")
                    .setContentText("New data available")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
        return result
    }
}