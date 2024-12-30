package com.yangian.callsync.feature.onboard.ui

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.icon.ArrowBackIcon
import com.yangian.callsync.core.designsystem.theme.AppTheme
import com.yangian.callsync.core.firebase.repository.DummyFirestoreRepository
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.core.network.model.DkmaManufacturer
import com.yangian.callsync.feature.onboard.DkmaUiState
import com.yangian.callsync.feature.onboard.OnBoardViewModel
import com.yangian.callsync.feature.onboard.R
import com.yangian.callsync.feature.onboard.model.OnBoardingScreens
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.Connection1Screen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.Connection2Screen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.DkmaScreen
import com.yangian.callsync.feature.onboard.ui.onBoardScreens.InstallScreen
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
                            || windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
                        ) {
                            MaterialTheme.typography.titleLarge
                        } else MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    AnimatedVisibility(
                        visibleState = MutableTransitionState(
                            currentScreen != OnBoardingScreens.Welcome
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
                        if (currentScreen == OnBoardingScreens.Welcome) {
                            stringResource(R.string.agree)
                        } else {
                            stringResource(id = R.string.proceed)
                        },
                    )
                }
            }
        },
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.safeContent)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->

        val commonModifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(dimensionResource(R.dimen.padding_medium))

        when (currentScreen) {

            OnBoardingScreens.Welcome -> WelcomeScreen(
                commonModifier
            )

            OnBoardingScreens.DkmaScreen -> DkmaScreen(
                dkmaUiState,
                isIssueVisible,
                isSolutionVisible,
                alterIssueVisibility,
                alterSolutionVisibility,
                loadDkmaManufacturer,
                commonModifier.verticalScroll(scrollState)
            )

            OnBoardingScreens.Install -> InstallScreen(
                createFirebaseAccount,
                commonModifier
            )

            OnBoardingScreens.Unlock -> UnlockScreen(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal,
                        ),
                    ),
            )

            OnBoardingScreens.Connection1 -> Connection1Screen(
                firestoreRepository,
                getFirebaseUser(),
                navigateToNextScreen,
                navigateToPreviousScreen,
                commonModifier
            )

            OnBoardingScreens.Connection2 -> Connection2Screen(
                getQrCode,
                updateOnBoardingCompleted,
                navigateToHome,
                registerLogsDownloadWorkRequest,
                commonModifier
            )
        }
    }
}

@Preview
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

    AppTheme {
        AppBackground {
            OnBoardRoute(
                currentScreen = OnBoardingScreens.Welcome,
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
                navigateToNextScreen = {},
                modifier = Modifier
            )
        }
    }
}