package com.yangian.callsync

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.callsync.core.workmanager.LogsDownloadWorker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import dagger.assisted.Assisted
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CallSyncApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workFactory: MyWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workFactory)
            .build()
}

class MyWorkerFactory @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val userPreferences: UserPreferences,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val callResourceRepository: CallResourceRepository,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = LogsDownloadWorker(
        firestore,
        firebaseAuth,
        firebaseAnalytics,
        userPreferences,
        callResourceRepository,
        appContext,
        workerParameters
    )
}
