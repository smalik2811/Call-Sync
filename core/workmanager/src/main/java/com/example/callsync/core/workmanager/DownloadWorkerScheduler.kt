package com.example.callsync.core.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.await

@HiltWorker
class DownloadWorkerScheduler(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        // Schedule One Time Work Request

        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()

        val logsDownloadWorkerRequest = OneTimeWorkRequestBuilder<LogsDownloadWorker>()
            .setConstraints(workerConstraints)
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        try {
            workManager.enqueueUniqueWork(
                "DOWNLOAD_CALL_LOGS",
                ExistingWorkPolicy.REPLACE,
                logsDownloadWorkerRequest
            ).await()

           return Result.success()

        } catch (e: Exception) {
            // Handle exception during enqueueing
            return Result.failure()
        }
    }
}