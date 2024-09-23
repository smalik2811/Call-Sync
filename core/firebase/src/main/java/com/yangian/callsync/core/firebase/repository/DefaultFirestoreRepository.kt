package com.yangian.callsync.core.firebase.repository

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.yangian.callsync.core.constant.Constant.FIRESTORE_CALL_COLLECTION_NAME
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.model.CallResource
import com.yangian.callsync.core.model.CryptoHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

sealed interface FirestoreResult {
    data object Failure : FirestoreResult
    data object Success : FirestoreResult
    data object Retry : FirestoreResult
}

class DefaultFirestoreRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirestoreRepository {
    private val TAG = "DOWNLOAD_WORKER"

    override fun createNewDocument(
        senderId: String,
        receiverId: String,
        onSuccessEvent: () -> Unit,
        onFailureEvent: () -> Unit
    ) {
        val documentRef =
            firebaseFirestore.collection(FIRESTORE_CALL_COLLECTION_NAME).document(receiverId)
        firebaseFirestore.runTransaction { transaction ->
            val snapshot = transaction.get(documentRef)
            if (snapshot.exists()) {
                if (snapshot.contains("sender")) {
                    if (snapshot.getString("sender") != senderId) {
                        throw FirebaseFirestoreException(
                            "Sender ID mismatch",
                            FirebaseFirestoreException.Code.ALREADY_EXISTS
                        )
                    } else {
                        val data = hashMapOf<String, Any>()
                        if (!snapshot.contains("array")) {
                            data["array"] = listOf<String>()
                        }
                        if (!snapshot.contains("ver")) {
                            data["ver"] = "1.0.0"
                        }
                        transaction.update(documentRef, data)
                    }
                } else {
                    val data = hashMapOf(
                        "array" to listOf<String>(),
                        "sender" to senderId,
                        "ver" to "1.0.0"
                    )
                    transaction.set(documentRef, data)
                }
            } else {
                val data = hashMapOf(
                    "array" to listOf<String>(),
                    "sender" to senderId,
                    "ver" to "1.0.0"
                )
                transaction.set(documentRef, data)
            }
        }.addOnSuccessListener {
            onSuccessEvent()
        }.addOnFailureListener { exception ->
            Log.i(
                "ONBOARD-CONNECTION-SCREEN-1",
                "Firebase Transaction Failed with exception: $exception"
            )
            onFailureEvent()
        }
    }

    override suspend fun addData(
        senderId: String,
        receiverId: String,
        callResourceRepository: CallResourceRepository,
    ): FirestoreResult = coroutineScope {
        var result: FirestoreResult = FirestoreResult.Success
        val data = hashMapOf<String, Any>()
        val documentRef =
            firebaseFirestore.collection(FIRESTORE_CALL_COLLECTION_NAME).document(receiverId)

        try {
            val document = suspendCoroutine<DocumentSnapshot> { continuation ->
                documentRef.get()
                    .addOnSuccessListener { document -> continuation.resume(document) }
                    .addOnFailureListener { e -> continuation.resumeWithException(e) }
            }

            if (!document.exists()) {
                throw FirebaseFirestoreException(
                    "Document does not exist",
                    FirebaseFirestoreException.Code.NOT_FOUND
                )
            }

            val cloudSenderId = document.getString("sender")

            Log.i(TAG, "CloudSenderId: $cloudSenderId")

            // Handle the case where cloudSenderId might be null
            if (cloudSenderId == null) {
                Log.e(TAG, "Error: 'sender' field is null in Firestore document")
                return@coroutineScope FirestoreResult.Failure // Example: Return a failure result
            } else if (senderId != cloudSenderId) {
                throw FirebaseFirestoreException(
                    "Sender ID mismatch",
                    FirebaseFirestoreException.Code.ALREADY_EXISTS
                )
            }

            val array = document.get("array") as List<*>

            if (array.isNotEmpty()) {
                Log.i(TAG, "Data found in cloud.")

                val callResourceList = array.map {
                    CallResource.toObject(it.toString(), CryptoHandler(senderId))
                }

                async { callResourceRepository.addCalls(callResourceList) }.join()
                Log.i(TAG, "Stored data in local database.")
                data["array"] = arrayListOf<String>()
            }

            data["download_timestamp"] = FieldValue.serverTimestamp()

            try {
                suspendCoroutine<Void> { continuation ->
                    documentRef.set(data, SetOptions.merge())
                        .addOnFailureListener { e -> continuation.resumeWithException(e) }
                }
            } catch (e: Exception) {
                Log.i(TAG, "Error while updating the Firestore Document: ${e.message}")
                result = FirestoreResult.Retry
            }

        } catch (e: Exception) {
            Log.i(TAG, "Error while getting the Firestore Document: ${e.message}")
            result =
                if (e is FirebaseFirestoreException && e.code == FirebaseFirestoreException.Code.NOT_FOUND) {
                    FirestoreResult.Retry
                } else {
                    FirestoreResult.Failure
                }
        }

        result
    }
}

class DummyFirestoreRepository : FirestoreRepository {
    override fun createNewDocument(
        senderId: String,
        receiverId: String,
        onSuccessEvent: () -> Unit,
        onFailureEvent: () -> Unit
    ) {

    }

    override suspend fun addData(
        senderId: String,
        receiverId: String,
        callResourceRepository: CallResourceRepository,
    ): FirestoreResult {
        return FirestoreResult.Success
    }

}