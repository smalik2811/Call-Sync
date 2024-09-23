package com.yangian.callsync

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.core.workmanager.LogsDownloadWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CallSyncApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        val channelId = "call_sync_notifications"
        val channelName = "Call Log Sync"
        val channelDescription = "Notification about call log sync status."

        val notificationManager: NotificationManager =
            getSystemService(NotificationManager::class.java)

        if (notificationManager.getNotificationChannel(channelId) == null) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = listOf(
                NotificationChannel(channelId, channelName, importance).apply {
                    description = channelDescription
                }
            )
            notificationManager.createNotificationChannels(channel)
        }
    }

    @Inject
    lateinit var workFactory: MyWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(workFactory)
            .build()
}

class MyWorkerFactory @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuth: FirebaseAuth,
    private val userPreferences: UserPreferences,
    private val callResourceRepository: CallResourceRepository,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = LogsDownloadWorker(
        appContext,
        workerParameters,
        firestoreRepository,
        firebaseAuth,
        userPreferences,
        callResourceRepository,
    )
}
