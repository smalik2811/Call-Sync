package com.example.callsync.core.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
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
    @Assisted private val firestore: FirebaseFirestore,
    @Assisted private val firebaseAuth: FirebaseAuth,
    @Assisted private val userPreference: UserPreferences,
    @Assisted private val callResourceRepository: CallResourceRepository,
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            // Get current user
            val currentUser = firebaseAuth.currentUser ?: return Result.failure() // No user, no logs to download

            // Get document reference
            val documentRef = firestore.collection("logs").document(currentUser.uid)

            // Fetch document
            val documentSnapshot = documentRef.get().await() // Await document retrieval
            val ready = documentSnapshot.getBoolean("ready") as Boolean // Check if ready

            // Process logs if document exists
            if (ready) {
                // Get logs array
                val logsArray = documentSnapshot.get("array") as? List<*> ?: emptyList<Map<String, Any>>()

                // Parse call resources
                val callResources = parseCallResources(logsArray)

                // Add call resources to local database (sequentially)
                withContext(Dispatchers.IO) { // Use withContext for database operations
                    callResourceRepository.addCalls(callResources)
                }

                // Set logs-array to null (sequentially)
                withContext(Dispatchers.IO) {
                    documentRef.update("ready", false).await() // Await update completion
                }
            }

            Result.success()

        } catch (e: Exception) {
            Result.failure()
        }
    }

    // Helper function for awaiting Firestore operations
    private suspend fun <T> Task<T>.await(): T {
        return suspendCoroutine { continuation ->
            addOnSuccessListener { continuation.resume(it) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
    }
}