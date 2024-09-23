package com.yangian.callsync.feature.home

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.callsync.core.workmanager.LogsDownloadWorker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.ui.CallFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    fun downloadLogs(context: Context) {
        val workerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<LogsDownloadWorker>()
            .setConstraints(workerConstraints)
//            .setBackoffCriteria(BackoffPolicy.LINEAR, 15, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork(
            "ONE-TIME-LOGS-DOWNLOAD",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun signout(
        context: Context,
        navigateToOnboarding: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                // 1. Cancel the existing worker
                WorkManager.getInstance(context).cancelAllWork()

                // 2. Delete the Firestore document
                val currentUserId: String =
                    async { firebaseAuth.currentUser?.uid }.await().toString()
                firestore.collection("logs").document(currentUserId).delete()

                // 3. Clear the Local Database
                callResourceRepository.deleteCalls()

                // 4. Clear the datastore
                userPreferences.clear()
            }.join()

            // 5. Take to the Onboarding Screen
            navigateToOnboarding()
        }
    }
}