package com.yangian.callsync.feature.onboard

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yangian.callsync.core.datastore.UserPreferences
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.core.network.model.DkmaManufacturer
import com.yangian.callsync.core.network.retrofit.RetrofitDkmaNetwork
import com.yangian.callsync.core.workmanager.LogsDownloadWorker
import com.yangian.callsync.feature.onboard.model.OnBoardingScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import qrcode.QRCode
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed interface DkmaUiState {
    data class Success(
        val dkmaManufacturer: DkmaManufacturer
    ) : DkmaUiState

    object Error : DkmaUiState
    object Loading : DkmaUiState
}

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val firebaseAuth: FirebaseAuth,
    val firestoreRepository: FirestoreRepository,
    private val datasource: RetrofitDkmaNetwork,
) : ViewModel() {

    // Ui
    private val _currentScreen =
        MutableStateFlow(OnBoardingScreens.TermsOfService)
    val currentScreen: StateFlow<OnBoardingScreens> = _currentScreen


    fun navigateToNextScreen() {
        viewModelScope.launch {
            when (_currentScreen.value) {
                OnBoardingScreens.TermsOfService -> _currentScreen.value = OnBoardingScreens.Welcome
                OnBoardingScreens.Welcome -> _currentScreen.value = OnBoardingScreens.DkmaScreen
                OnBoardingScreens.DkmaScreen -> _currentScreen.value = OnBoardingScreens.Install
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
                OnBoardingScreens.Install -> _currentScreen.value = OnBoardingScreens.DkmaScreen
                OnBoardingScreens.DkmaScreen -> _currentScreen.value = OnBoardingScreens.Welcome
                OnBoardingScreens.Welcome -> _currentScreen.value = OnBoardingScreens.TermsOfService
                OnBoardingScreens.TermsOfService -> {}
            }
        }
    }

    // Firebase
    private var firebaseUser: FirebaseUser? = firebaseAuth.currentUser

    fun getFirebaseUser(): String {
        if (firebaseUser == null) {
            createFirebaseAccount()
        }
        firebaseUser = firebaseAuth.currentUser
        return firebaseUser!!.uid
    }

    fun createFirebaseAccount() {
        firebaseAuth.signInAnonymously()
    }

    // Dkma
    private val manufacturerName = Build.MANUFACTURER
    var isIssueVisible by mutableStateOf(false)
        private set
    var isSolutionVisible by mutableStateOf(false)
        private set
    var dkmaUiState: DkmaUiState by mutableStateOf(DkmaUiState.Loading)

    fun loadDkmaManufacturer() {
        viewModelScope.launch {
            dkmaUiState = try {
                DkmaUiState.Success(
                    dkmaManufacturer = datasource.getDkmaManufacturer(
                        manufacturerName
                    )
                )
            } catch (e: Exception) {
                Log.e("DkmaViewModel", "Error loading DKMA Manufacturer", e)
                DkmaUiState.Error
            }
        }
    }

    fun alterIssueVisibility() {
        isIssueVisible = !isIssueVisible
    }

    fun alterSolutionVisibility() {
        isSolutionVisible = !isSolutionVisible
    }

    // User Preferences

    fun updateOnBoardingCompleted(
        newOnboardingState: Boolean,
    ) {
        userPreferences.setOnboardingDone(newOnboardingState)
    }

    fun registerLogsDownloadWorkRequest(
        context: Context,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingWorkPolicy = userPreferences.getWorkerRetryPolicy()
            val workerConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<LogsDownloadWorker>(
                repeatInterval = existingWorkPolicy,
                repeatIntervalTimeUnit = TimeUnit.HOURS,
            ).setConstraints(workerConstraints)
                .build()

            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniquePeriodicWork(
                "LOGS_DOWNLOAD_WORKER",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
        }
    }

    // Qrcode
    fun getQrCode(backgroundColor: Int, foregroundColor: Int): ImageBitmap {
        val byteArray: ByteArray = QRCode
            .ofSquares()
            .withBackgroundColor(backgroundColor)
            .withColor(foregroundColor)
            .build(getFirebaseUser())
            .renderToBytes()

        return BitmapFactory
            .decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
    }

    init {
        loadDkmaManufacturer()
    }
}