package com.example.callsync.core.workmanager

import android.content.Context
import android.os.Bundle
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.model.CallResource
import com.yangian.callsync.core.model.CryptoHandler
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@HiltWorker
class LogsDownloadWorker @AssistedInject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseAnalytics: FirebaseAnalytics,
    @Assisted private val userPreferences: UserPreferences,
    private val callResourceRepository: CallResourceRepository,
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        var result: Result = Result.retry()
        try {
            // Get current user
            val currentUserId: String = firebaseAuth.currentUser?.uid!!
            val senderId: String = runBlocking {
                userPreferences.getSenderId().first()
            }
            val cryptoHandler: CryptoHandler = CryptoHandler(senderId)

            // Get document reference
            val documentRef = firestore.collection("logs").document(currentUserId)
            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(documentRef)
                if (!snapshot.exists()) {
                    throw FirebaseFirestoreException(
                        "Document does not exist",
                        FirebaseFirestoreException.Code.NOT_FOUND
                    )
                }

                val cloudSender: String = snapshot.getString("sender").toString()

                if (cloudSender != senderId) {
                    // Some other Sender id already present, can not continue.
                    // This should not happen
                    throw FirebaseFirestoreException(
                        "Sender ID mismatch",
                        FirebaseFirestoreException.Code.ALREADY_EXISTS
                    )
                }

                firebaseAnalytics.logEvent("firestore_document_checked", Bundle().apply {
                    putString("timestamp", System.currentTimeMillis().toString())
                    putString("user_id", currentUserId)
                })

                val arrayData: List<String> = snapshot["array"] as List<String>


                if (arrayData.isNotEmpty()) {
                    val callResourceList: List<CallResource> = arrayData.map {
                        CallResource.toObject(it, cryptoHandler)
                    }

                    firebaseAnalytics.logEvent("firestore_logs_fetched", Bundle().apply {
                        putString("timestamp", System.currentTimeMillis().toString())
                        putString("user_id", currentUserId)
                    })

                    // Add call resources to local database (sequentially)
                    val scope = CoroutineScope(Dispatchers.IO)
                    scope.launch {
                        callResourceRepository.addCalls(callResourceList)
                    }

                    val data = hashMapOf<String, Any>(
                        "array" to arrayListOf<String>(),
                        "download_timestamp" to FieldValue.serverTimestamp()
                    )
                    transaction.set(documentRef, data, SetOptions.merge())

                    firebaseAnalytics.logEvent("firestore_logs_status_updated", Bundle().apply {
                        putString("timestamp", System.currentTimeMillis().toString())
                        putString("user_id", currentUserId)
                    })
                } else {
                    val data = hashMapOf<String, Any>(
                        "download_timestamp" to FieldValue.serverTimestamp()
                    )
                    transaction.set(documentRef, data, SetOptions.merge())
                }
            }.addOnSuccessListener {
                result = Result.success()
            }.addOnFailureListener {
                result = Result.failure()
            }
        } catch (e: Exception) {
            return Result.retry()
        }
        return result
    }
}