package com.example.callsync.core.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.auth.FirebaseAuth
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.core.firebase.repository.FirestoreResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class LogsDownloadWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuth: FirebaseAuth,
    private val userPreferences: UserPreferences,
    private val callResourceRepository: CallResourceRepository,
) : CoroutineWorker(context, workerParams) {

    private val TAG = "DOWNLOAD_WORKER"

    override suspend fun doWork(): Result {
        lateinit var result: Result
        try {
            // Get current user
            val currentUserId: String = firebaseAuth.currentUser?.uid!!
            val senderId: String = userPreferences.getSenderId().first()
            Log.i(TAG, "SenderId: $senderId")
            Log.i(TAG, "Worker Started.")
            val firestoreResult = firestoreRepository.addData(
                senderId,
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
        return result
    }
}