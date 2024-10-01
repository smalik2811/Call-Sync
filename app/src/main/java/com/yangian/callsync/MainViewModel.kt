package com.yangian.callsync

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.navigation.CallSyncDestination
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _isSplashVisible: MutableState<Boolean> = mutableStateOf(true)
    val isSplashVisible: MutableState<Boolean> = _isSplashVisible

    private val _startDestination: MutableState<String> =
        mutableStateOf(CallSyncDestination.OnBoard.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            userPreferences.getOnboardingDone().collect { completed ->
                if (completed) {
                    _startDestination.value = CallSyncDestination.Home.route
                } else {
                    _startDestination.value = CallSyncDestination.OnBoard.route
                }
            }
            _isSplashVisible.value = false
        }
    }
}