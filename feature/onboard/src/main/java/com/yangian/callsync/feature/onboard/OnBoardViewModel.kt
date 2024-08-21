package com.yangian.callsync.feature.onboard

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.callsync.core.workmanager.DownloadWorkerScheduler
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.feature.onboard.model.OnBoardingScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    privateFirebaseAuth: FirebaseAuth,
    privateFirebaseFirestore: FirebaseFirestore,
) : ViewModel() {

    val firebaseAuth = privateFirebaseAuth
    val firebaseFirestore = privateFirebaseFirestore

    private val _currentScreen =
        MutableStateFlow(OnBoardingScreens.TermsOfService)
    val currentScreen: StateFlow<OnBoardingScreens> = _currentScreen

    fun navigateToNextScreen() {
        viewModelScope.launch {
            when (_currentScreen.value) {
                OnBoardingScreens.TermsOfService -> _currentScreen.value = OnBoardingScreens.Welcome
                OnBoardingScreens.Welcome -> _currentScreen.value = OnBoardingScreens.Install
                OnBoardingScreens.Install -> _currentScreen.value = OnBoardingScreens.Unlock
                OnBoardingScreens.Unlock -> _currentScreen.value = OnBoardingScreens.Connection1
                OnBoardingScreens.Connection1 -> _currentScreen.value =
                    OnBoardingScreens.Connection2

                OnBoardingScreens.Connection2 -> {}
            }
        }
    }

    fun navigateToPreviousScreen() {
        viewModelScope.launch {
            when (_currentScreen.value) {
                OnBoardingScreens.Connection2 -> _currentScreen.value =
                    OnBoardingScreens.Connection1

                OnBoardingScreens.Connection1 -> _currentScreen.value = OnBoardingScreens.Unlock
                OnBoardingScreens.Unlock -> _currentScreen.value = OnBoardingScreens.Install
                OnBoardingScreens.Install -> _currentScreen.value = OnBoardingScreens.Welcome
                OnBoardingScreens.Welcome -> _currentScreen.value = OnBoardingScreens.TermsOfService
                OnBoardingScreens.TermsOfService -> {}
            }
        }
    }

    fun updateOnBoardingCompleted(
        newOnboardingState: Boolean,
        firebaseAnalytics: FirebaseAnalytics?
    ) {
        viewModelScope.launch {
            userPreferences.setOnboardingDone(newOnboardingState)
            firebaseAnalytics?.logEvent("Onboarding_Completed", Bundle().apply {
                putString("user_id", firebaseAuth.currentUser?.uid)
            })
        }
    }

    fun registerLogsDownloadWorkRequest(
        context: Context,
        firebaseAnalytics: FirebaseAnalytics?
    ) {

        viewModelScope.launch {

            val workRequest = PeriodicWorkRequestBuilder<DownloadWorkerScheduler>(
                repeatInterval = 6,
                repeatIntervalTimeUnit = TimeUnit.HOURS,
                flexTimeInterval = 3,
                flexTimeIntervalUnit = TimeUnit.HOURS
            ).build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniquePeriodicWork(
                "DOWNLOAD_CALL_LOGS_Scheduler",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )

            firebaseAnalytics?.logEvent("download_call_logs_scheduler_registered", Bundle().apply {
                putString("user_id", firebaseAuth.currentUser?.uid)
            })
        }
    }
}