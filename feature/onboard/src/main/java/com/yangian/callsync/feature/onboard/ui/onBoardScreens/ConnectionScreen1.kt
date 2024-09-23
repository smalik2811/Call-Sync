package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.Manifest
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.google.mlkit.vision.barcode.common.Barcode
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.CustomAlertDialog
import com.yangian.callsync.core.designsystem.component.QRCamera
import com.yangian.callsync.core.designsystem.icon.QrCodeScannerIcon
import com.yangian.callsync.core.designsystem.isPermissionGranted
import com.yangian.callsync.core.designsystem.openPermissionSetting
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.designsystem.theme.extendedDark
import com.yangian.callsync.core.designsystem.theme.extendedLight
import com.yangian.callsync.core.firebase.repository.DummyFirestoreRepository
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import com.yangian.callsync.feature.onboard.R

@Composable
fun ConnectionScreen1(
    firestoreRepository: FirestoreRepository,
    firebaseUser: String,
    navigateToNextScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    updateSenderIdPreference: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPreviewing: Boolean = false
) {
    val context = LocalContext.current
    var scannedValue by remember { mutableStateOf<String?>(null) }
    var hasScanned by remember { mutableStateOf(false) }
    val onBarCodeScanned: (Barcode?) -> Unit = { barcode ->
        barcode?.rawValue?.let { value ->
            if (!hasScanned) {
                scannedValue = value
                hasScanned = true
            }
        }
    }

    val rectangleBoundaryColor = if (isSystemInDarkTheme()) {
        extendedDark.success.color
    } else {
        extendedLight.success.color
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        if (!hasScanned) {
            Text(
                text = stringResource(R.string.num_sum_qr_code_scan_instruction),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                if (context.isPermissionGranted(Manifest.permission.CAMERA)) {
                    QRCamera().CameraPreviewView(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(1f)
                            .border(
                                dimensionResource(R.dimen.padding_tiny),
                                rectangleBoundaryColor,
                                RoundedCornerShape(
                                    dimensionResource(R.dimen.corner_radius_medium)
                                )
                            )
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_medium))),
                        onBarcodeScanned = onBarCodeScanned,
                        isPreviewing = isPreviewing
                    )
                } else {
                    val annotatedDialogMessage = buildAnnotatedString {
                        append(stringResource(R.string.camera_permission_explanation_part_1))
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(R.string.num_sum_name))
                        }
                        append(stringResource(R.string.camer_permisssion_explanation_part_2))
                    }
                    CustomAlertDialog(
                        onPositiveButtonClick = { context.openPermissionSetting() },
                        onNegativeButtonClick = { navigateToPreviousScreen() },
                        onDismissRequest = { navigateToPreviousScreen() },
                        dialogTitle = stringResource(R.string.camera_permission_request_title),
                        dialogText = annotatedDialogMessage,
                        positiveButtonText = stringResource(R.string.allow_camera),
                        negativeButtonText = stringResource(R.string.cancel),
                        iconContentDescriptionText = stringResource(R.string.qr_code_scanner),
                        modifier = Modifier,
                        icon = QrCodeScannerIcon
                    )
                }
            }
        } else {
            CircularProgressIndicator()

            if (scannedValue != null) {
                val senderId: String = scannedValue.toString()
                // UID found in the QR code
                firestoreRepository.createNewDocument(
                    senderId,
                    firebaseUser,
                    {
                        navigateToNextScreen()
                        updateSenderIdPreference(senderId)
                    },
                    navigateToPreviousScreen
                )

            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(device = "id:pixel_8_pro")
@Composable
private fun ConnectionScreen1Preview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            ConnectionScreen1(
                DummyFirestoreRepository(),
                stringResource(R.string.test_user),
                {},
                {},
                {},
                Modifier,
                true
            )
        }
    }
}