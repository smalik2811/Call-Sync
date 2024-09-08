package com.yangian.callsync.feature.home

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.ui.CallFeedUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    callResourceRepository: CallResourceRepository
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
}