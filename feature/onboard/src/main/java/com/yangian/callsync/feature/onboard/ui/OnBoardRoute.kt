package com.yangian.callsync.feature.onboard.ui

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.icon.ArrowBackIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.firebase.repository.DummyFirestoreRepository
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.core.network.model.DkmaManufacturer
import com.yangian.callsync.feature.onboard.DkmaUiState
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
import qrcode.QRCode

@Composable
fun OnBoardRoute(
    modifier: Modifier = Modifier,
    onBoardViewModel: OnBoardViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {},
) {
    val currentScreen by onBoardViewModel.currentScreen.collectAsState()

    OnBoardRoute(
        currentScreen = currentScreen,
        dkmaUiState = onBoardViewModel.dkmaUiState,
        isIssueVisible = onBoardViewModel.isIssueVisible,
        isSolutionVisible = onBoardViewModel.isSolutionVisible,
        alterIssueVisibility = onBoardViewModel::alterIssueVisibility,
        alterSolutionVisibility = onBoardViewModel::alterSolutionVisibility,
        firestoreRepository = onBoardViewModel.firestoreRepository,
        createFirebaseAccount = onBoardViewModel::createFirebaseAccount,
        getFirebaseUser = onBoardViewModel::getFirebaseUser,
        loadDkmaManufacturer = onBoardViewModel::loadDkmaManufacturer,
        getQrCode = onBoardViewModel::getQrCode,
        updateOnBoardingCompleted = onBoardViewModel::updateOnBoardingCompleted,
        registerLogsDownloadWorkRequest = onBoardViewModel::registerLogsDownloadWorkRequest,
        navigateToHome = navigateToHome,
        navigateToPreviousScreen = onBoardViewModel::navigateToPreviousScreen,
        navigateToNextScreen = onBoardViewModel::navigateToNextScreen,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardRoute(
    currentScreen: OnBoardingScreens,
    dkmaUiState: DkmaUiState,
    isIssueVisible: Boolean,
    isSolutionVisible: Boolean,
    alterIssueVisibility: () -> Unit,
    alterSolutionVisibility: () -> Unit,
    firestoreRepository: FirestoreRepository,
    createFirebaseAccount: () -> Unit,
    getFirebaseUser: () -> String,
    loadDkmaManufacturer: () -> Unit,
    getQrCode: (backgroundColor: Int, foregroundColor: Int) -> ImageBitmap,
    updateOnBoardingCompleted: (newOnboardingState: Boolean) -> Unit,
    registerLogsDownloadWorkRequest: (context: Context) -> Unit,
    navigateToHome: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    navigateToNextScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            currentScreen.title
                        ),
                        style = if (
                            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
                            || windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                            MaterialTheme.typography.titleLarge
                        } else MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
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
                            onClick = navigateToPreviousScreen,
                        ) {
                            Icon(
                                imageVector = ArrowBackIcon,
                                contentDescription = stringResource(R.string.go_back)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        floatingActionButton = {
            if (
                currentScreen != OnBoardingScreens.Connection2 &&
                currentScreen != OnBoardingScreens.Connection1
            ) {
                ExtendedFloatingActionButton(
                    navigateToNextScreen
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
        },
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        when (currentScreen) {
            OnBoardingScreens.TermsOfService -> TermsOfServiceScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .verticalScroll(scrollState)
            )

            OnBoardingScreens.Welcome -> WelcomeScreen(
                createFirebaseAccount,
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .windowInsetsPadding(WindowInsets.safeContent)
            )

            OnBoardingScreens.DkmaScreen -> DkmaScreen(
                dkmaUiState,
                isIssueVisible,
                isSolutionVisible,
                alterIssueVisibility,
                alterSolutionVisibility,
                loadDkmaManufacturer,
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .verticalScroll(scrollState)
            )

            OnBoardingScreens.Install -> InstallScreen(
                scaffoldPadding = it,
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeContent)
            )

            OnBoardingScreens.Unlock -> UnlockScreen(
                scaffoldPadding = it,
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .verticalScroll(scrollState)
            )

            OnBoardingScreens.Connection1 -> ConnectionScreen1(
                scaffoldPadding = it,
                firestoreRepository,
                getFirebaseUser(),
                navigateToNextScreen,
                navigateToPreviousScreen,
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .verticalScroll(scrollState)
            )

            OnBoardingScreens.Connection2 -> ConnectionScreen2(
                scaffoldingPadding = it,
                getQrCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .windowInsetsPadding(WindowInsets.safeContent)
                    .verticalScroll(scrollState)
            )
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun OnBoardRoutePreview() {

    val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
    val foregroundColor = MaterialTheme.colorScheme.onSurface.toArgb()
    val byteArray: ByteArray = QRCode
        .ofSquares()
        .withBackgroundColor(backgroundColor)
        .withColor(foregroundColor)
        .build(stringResource(R.string.test_user))
        .renderToBytes()

    val imageBitMap = BitmapFactory
        .decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()

    val dkmaManufacturer = DkmaManufacturer(
        explanation = stringResource(R.string.dkma_dummy_explanation),
        user_solution = stringResource(R.string.dkma_dummy_user_solution),
    )

    CallSyncAppTheme {
        CallSyncAppBackground {
            OnBoardRoute(
                currentScreen = OnBoardingScreens.Connection2,
                dkmaUiState = DkmaUiState.Success(dkmaManufacturer),
                isIssueVisible = false,
                isSolutionVisible = false,
                alterIssueVisibility = {},
                alterSolutionVisibility = {},
                firestoreRepository = DummyFirestoreRepository(),
                createFirebaseAccount = {},
                getFirebaseUser = { "" },
                loadDkmaManufacturer = {},
                getQrCode = { _, _ ->
                    imageBitMap
                },
                updateOnBoardingCompleted = {},
                registerLogsDownloadWorkRequest = {},
                navigateToHome = {},
                navigateToPreviousScreen = {},
                navigateToNextScreen = {}
            )
        }
    }
}