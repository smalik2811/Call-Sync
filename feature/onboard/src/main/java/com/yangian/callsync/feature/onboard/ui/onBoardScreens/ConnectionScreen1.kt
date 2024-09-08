package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.Manifest
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.yangian.callsync.core.designsystem.cameraPermissionRequest
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.QRCamera
import com.yangian.callsync.core.designsystem.isPermissionGranted
import com.yangian.callsync.core.designsystem.openPermissionSetting
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.designsystem.theme.extendedDark
import com.yangian.callsync.core.designsystem.theme.extendedLight
import com.yangian.callsync.feature.onboard.OnBoardViewModel

@Composable
fun ConnectionScreen1(
    onBoardViewModel: OnBoardViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Text(
            text = "Scan this QR code using Num Sum App.",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current
        val camera = remember {
            QRCamera()
        }

        var lastScannedBarcode by remember {
            mutableStateOf<String?>(null)
        }

        val rectangleBoundaryColor = if (isSystemInDarkTheme()) {
            extendedDark.success.color
        } else {
            extendedLight.success.color
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            if (context.isPermissionGranted(Manifest.permission.CAMERA)) {
                camera.CameraPreview(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f)
                        .border(2.dp, rectangleBoundaryColor, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    onBarcodeScanned = { barcode ->
                        barcode?.displayValue?.let {
                            lastScannedBarcode = it

                            var senderId: String? = null
                            if (!lastScannedBarcode.isNullOrBlank()) {
                                senderId = lastScannedBarcode
                            }

                            senderId?.let {
                                // UID found in the QR code
                                val db = onBoardViewModel.firebaseFirestore
                                val currentUser = onBoardViewModel.firebaseAuth.currentUser?.uid
                                currentUser?.let {
                                    val documentRef = db.collection("logs").document(currentUser)
                                    // If the document exists check the existing sender id, if the sender id don't match then there is an issue.
                                    // If the document exists but sender id is null or match, then add the rest of the data or set senderid
                                    // If the document don't exists set everything.
                                    db.runTransaction { transaction ->
                                        val snapshot = transaction.get(documentRef)
                                        if (snapshot.exists()) {
                                            if (snapshot.contains("sender")) {
                                                if (snapshot.getString("sender") != senderId) {
                                                    // Some other Sender id already present, can not continue.
                                                    // This should not happen
                                                    throw FirebaseFirestoreException(
                                                        "Sender ID mismatch",
                                                        FirebaseFirestoreException.Code.ALREADY_EXISTS
                                                    )
                                                } else {
                                                    // Same Sender id already exists, set the remaining fields if not exists
                                                    val data = hashMapOf<String, Any>()

                                                    if (!snapshot.contains("array")) {
                                                        data["array"] = listOf<String>()
                                                    }

                                                    if (!snapshot.contains("ver")) {
                                                        data["ver"] = "1.0.0"
                                                    }

                                                    transaction.update(documentRef, data)
                                                }
                                            } else {
                                                // No Sender id present, add Sender id along with other fields
                                                val data = hashMapOf(
                                                    "array" to listOf<String>(),
                                                    "sender" to senderId,
                                                    "ver" to "1.0.0"
                                                )
                                                transaction.update(documentRef, data)
                                            }
                                        } else {
                                            // Snapshot does not exists, set the data
                                            val data = hashMapOf(
                                                "array" to listOf<String>(),
                                                "sender" to senderId,
                                                "ver" to "1.0.0"
                                            )
                                            transaction.set(documentRef, data)
                                        }
                                    }.addOnSuccessListener {
                                        onBoardViewModel.navigateToNextScreen()
                                    }.addOnFailureListener { exception ->
                                        Toast.makeText(
                                            context,
                                            exception.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        onBoardViewModel.navigateToPreviousScreen()
                                    }
                                }
                            }
                        }
                    }
                )
            } else {
                CircularProgressIndicator()
                context.cameraPermissionRequest {
                    context.openPermissionSetting()
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun ConnectionScreen1Preview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            ConnectionScreen1(onBoardViewModel = hiltViewModel())
        }
    }
}