package com.yangian.callsync.feature.home

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.model.CallResource
import com.yangian.callsync.core.model.CryptoHandler
import com.yangian.callsync.core.ui.CallFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val callResourceRepository: CallResourceRepository,
    private val firebaseAuth: FirebaseAuth,
    private val userPreferences: UserPreferences,
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    private val _focussedCallResourceId = MutableStateFlow(0L)
    val focussedCallResourceId: StateFlow<Long> = _focussedCallResourceId.asStateFlow()

    val feedState: StateFlow<CallFeedUiState> = callResourceRepository.getCalls()
        .map(CallFeedUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CallFeedUiState.Loading
        )

    val snackBarHostState = SnackbarHostState()

    fun updateFocussedStateFlow(newFocussedCallResourceId: Long) {
        if (newFocussedCallResourceId == _focussedCallResourceId.value) {
            _focussedCallResourceId.value = -1L
        } else {
            _focussedCallResourceId.value = newFocussedCallResourceId
        }
    }

    suspend fun downloadLogs() {

        val currentUserId: String = firebaseAuth.currentUser?.uid!!
        val senderId: String = runBlocking {
            userPreferences.getSenderId().first()
        }
        val cryptoHandler = CryptoHandler(senderId)

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

            val arrayData: List<String> = snapshot["array"] as List<String>

            if (arrayData.isNotEmpty()) {
                val callResourceList: List<CallResource> = arrayData.map {
                    CallResource.toObject(it, cryptoHandler)
                }

                // Add call resources to local database (sequentially)
                viewModelScope.launch {
                    callResourceRepository.addCalls(callResourceList)
                }

                val data = hashMapOf<String, Any>(
                    "array" to arrayListOf<String>(),
                    "download_timestamp" to FieldValue.serverTimestamp()
                )
                transaction.set(documentRef, data, SetOptions.merge())

            } else {
                val data = hashMapOf<String, Any>(
                    "download_timestamp" to FieldValue.serverTimestamp()
                )
                transaction.set(documentRef, data, SetOptions.merge())
            }
        }
    }

    fun signout(
        context: Context,
        navigateToOnboarding: () -> Unit
    ) {
        // 1. Cancel the existing worker
        WorkManager.getInstance(context).cancelAllWork()

        // 2. Delete the Firestore document
        val currentUserId: String = firebaseAuth.currentUser?.uid!!
        firestore.collection("logs").document(currentUserId).delete()

        // 3. Clear the Local Database
        viewModelScope.launch {
            callResourceRepository.deleteCalls()
        }

        // 4. Clear the datastore
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.clear()
        }

        // 5. Take to the Onboarding Screen
        navigateToOnboarding()
    }
}