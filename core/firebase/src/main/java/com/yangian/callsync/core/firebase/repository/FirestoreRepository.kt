package com.yangian.callsync.core.firebase.repository

import com.yangian.callsync.core.data.repository.CallResourceRepository

interface FirestoreRepository {

    fun createNewDocument(
        senderId: String,
        receiverId: String,
        onSuccessEvent: () -> Unit,
        onFailureEvent: () -> Unit
    )

    suspend fun addData(
        receiverId: String,
        callResourceRepository: CallResourceRepository,
    ): FirestoreResult

    suspend fun handShake(
        receiverId: String,
        encryptedHandShakeKey: String,
        onSuccessEvent: () -> Unit,
        onFailureEvent: () -> Unit
    )
}