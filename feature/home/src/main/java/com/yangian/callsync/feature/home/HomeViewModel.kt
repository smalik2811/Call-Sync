package com.yangian.callsync.feature.home

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.model.CallResource.Companion.parseCallResources
import com.yangian.callsync.core.ui.CallFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val callResourceRepository: CallResourceRepository
) : ViewModel() {

    private val _focussedCallResourceId = MutableStateFlow(0L)
    val focussedCallResourceId: StateFlow<Long> = _focussedCallResourceId.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

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

    fun downloadLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            // Get current user
            val currentUser = firebaseAuth.currentUser // No user, no logs to download

            if (currentUser != null) {
                // Get document reference
                val documentRef = firebaseFirestore.collection("logs").document(currentUser.uid)

                // Fetch document
                val documentSnapshot = documentRef.get().await() // Await document retrieval
                val ready = documentSnapshot.getBoolean("ready") as Boolean // Check if ready

                // Process logs if document exists
                if (ready) {
                    // Get logs array
                    val logsArray =
                        documentSnapshot.get("array") as? List<*> ?: emptyList<Map<String, Any>>()

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
            }
        }
    }
}