package com.yangian.callsync.feature.onboard.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.nativead.NativeAd
import com.google.firebase.analytics.FirebaseAnalytics
import com.yangian.callsync.core.designsystem.BuildConfig
import com.yangian.callsync.core.designsystem.component.admob.loadNativeAd
import com.yangian.callsync.core.designsystem.icon.ArrowBackIcon
import com.yangian.callsync.feature.onboard.OnBoardViewModel
import com.yangian.callsync.feature.onboard.R
import com.yangian.callsync.feature.onboard.model.OnBoardingScreens
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.ConnectionScreen1
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.ConnectionScreen2
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.DkmaScreen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.InstallScreen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.TermsOfServiceScreen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.UnlockScreen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardRoute(
    modifier: Modifier = Modifier,
    onBoardViewModel: OnBoardViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {},
    firebaseAnalytics: FirebaseAnalytics?
) {
    val currentScreen by onBoardViewModel.currentScreen.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            currentScreen.title
                        ),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                navigationIcon = {
                    AnimatedVisibility(
                        visibleState = MutableTransitionState(
                            currentScreen != OnBoardingScreens.TermsOfService
                        ),
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally()
                    ) {
                        IconButton(
                            onClick = onBoardViewModel::navigateToPreviousScreen
                        ) {
                            Icon(
                                imageVector = ArrowBackIcon,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    }
                },
                modifier = Modifier
            )
        },
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            OnBoardingScreen(
                onBoardViewModel = onBoardViewModel,
                navigateToHome = navigateToHome,
                firebaseAnalytics = firebaseAnalytics,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            if (
                currentScreen != OnBoardingScreens.Connection2 &&
                currentScreen != OnBoardingScreens.Connection1
            ) {
                Spacer(
                    modifier = Modifier.height(dimensionResource(R.dimen.padding_small))
                )

                Button(
                    onClick = onBoardViewModel::navigateToNextScreen,
                ) {
                    Text(
                        text =
                        if (currentScreen == OnBoardingScreens.TermsOfService) {
                            stringResource(R.string.agree_and_proceed)
                        } else {
                            stringResource(id = R.string.proceed)
                        },
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.padding_small))
            )
        }
    }

}

@Composable
fun OnBoardingScreen(
    onBoardViewModel: OnBoardViewModel,
    navigateToHome: () -> Unit,
    firebaseAnalytics: FirebaseAnalytics?,
    modifier: Modifier = Modifier
) {
    val currentScreen by onBoardViewModel.currentScreen.collectAsState()

    when (currentScreen) {
        OnBoardingScreens.TermsOfService -> TermsOfServiceScreen(modifier)
        OnBoardingScreens.Welcome -> WelcomeScreen(
            onBoardViewModel::createFirebaseAccount,
            modifier
        )

        OnBoardingScreens.Install -> InstallScreen(modifier)
        OnBoardingScreens.Unlock -> UnlockScreen(modifier)
        OnBoardingScreens.Connection1 -> ConnectionScreen1(
            onBoardViewModel.firestoreRepository,
            onBoardViewModel.getFirebaseUser(),
            onBoardViewModel::navigateToNextScreen,
            onBoardViewModel::navigateToPreviousScreen,
            modifier
        )

        OnBoardingScreens.Connection2 -> ConnectionScreen2(
            onBoardViewModel::getQrCode,
            onBoardViewModel::updateOnBoardingCompleted,
            navigateToHome,
            onBoardViewModel::registerLogsDownloadWorkRequest,
            firebaseAnalytics,
            modifier,
        )

        OnBoardingScreens.DkmaScreen -> DkmaScreen(
            onBoardViewModel.dkmaUiState,
            onBoardViewModel.isIssueVisible,
            onBoardViewModel.isSolutionVisible,
            onBoardViewModel::alterIssueVisibility,
            onBoardViewModel::alterSolutionVisibility,
            onBoardViewModel::loadDkmaManufacturer,
            modifier
        )
    }
}