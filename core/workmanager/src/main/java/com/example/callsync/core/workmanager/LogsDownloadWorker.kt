package com.example.callsync.core.workmanager

import android.content.Context
import android.os.Bundle
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.model.CallResource.Companion.parseCallResources
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@HiltWorker
class LogsDownloadWorker @AssistedInject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val callResourceRepository: CallResourceRepository,
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        try {
            withContext(Dispatchers.IO) {
                // Get current user
                val currentUserId: String = firebaseAuth.currentUser?.uid!!

                // Get document reference
                val documentRef = firestore.collection("logs").document(currentUserId)

                firebaseAnalytics.logEvent("firestore_document_checked", Bundle().apply {
                    putString("timestamp", System.currentTimeMillis().toString())
                    putString("user_id", currentUserId)
                })

                // Fetch document
                val documentSnapshot = documentRef.get().await() // Await document retrieval
                val ready = documentSnapshot.getBoolean("ready") as Boolean // Check if ready

                firebaseAnalytics.logEvent("firestore_status_checked", Bundle().apply {
                    putString("timestamp", System.currentTimeMillis().toString())
                    putString("user_id", currentUserId)
                    putBoolean("result", ready)
                })

                if (ready) {
                    // Get logs array
                    val logsArray = documentSnapshot.get("array")
                    val parsedLogs = if (logsArray is List<*>) {
                        logsArray.mapNotNull { it as? Map<*, *> }
                    } else {
                        emptyList<Map<String, Any>>()
                    }


                    firebaseAnalytics.logEvent("firestore_logs_fetched", Bundle().apply {
                        putString("timestamp", System.currentTimeMillis().toString())
                        putString("user_id", currentUserId)
                    })

                    // Parse call resources
                    val callResources = parseCallResources(parsedLogs)

                    // Add call resources to local database (sequentially)
                    withContext(Dispatchers.IO) { // Use withContext for database operations
                        callResourceRepository.addCalls(callResources)
                    }

                    firebaseAnalytics.logEvent("logs_stored_locally", Bundle().apply {
                        putString("timestamp", System.currentTimeMillis().toString())
                        putString("user_id", currentUserId)
                    })

                    documentRef.update("ready", false).await() // Await update completion

                    firebaseAnalytics.logEvent("firestore_logs_status_updated", Bundle().apply {
                        putString("timestamp", System.currentTimeMillis().toString())
                        putString("user_id", currentUserId)
                    })
                }
            }
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
}

// Helper function for awaiting Firestore operations
private suspend fun <T> Task<T>.await(): T {
    return suspendCoroutine { continuation ->
        addOnSuccessListener { continuation.resume(it) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }
}