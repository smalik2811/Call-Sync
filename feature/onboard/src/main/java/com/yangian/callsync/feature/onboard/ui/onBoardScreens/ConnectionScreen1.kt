package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.moduleinstall.InstallStatusListener
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.OnBoardViewModel

@Composable
fun ConnectionScreen1(
    onBoardViewModel: OnBoardViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.weight(1f))

        val context = LocalContext.current
        var isModuleAvailable by remember {
            mutableStateOf(false)
        }
        var downloadProgress by remember { mutableIntStateOf(0) }

        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
            )
            .enableAutoZoom()
            .build()


        // Check if the module is installed
        val moduleInstallClient = ModuleInstall.getClient(context)

        val scanner = GmsBarcodeScanning.getClient(context, options)

        class ModuleInstallProgressListener : InstallStatusListener {
            override fun onInstallStatusUpdated(update: ModuleInstallStatusUpdate) {
                // Progress info is only set when modules are in the progress of downloading.
                update.progressInfo?.let {
                    val progress = (it.bytesDownloaded * 100 / it.totalBytesToDownload).toInt()
                    // Set the progress for the progress bar.
                    downloadProgress = progress
                }

                if (update.progressInfo?.bytesDownloaded == update.progressInfo?.totalBytesToDownload) {
                    // Download completed, unregister listener and update state
                    moduleInstallClient.unregisterListener(this)
                    isModuleAvailable = true
                } else if (isTerminateState(update.installState)) {
                    moduleInstallClient.unregisterListener(this)
                }
            }

            fun isTerminateState(@ModuleInstallStatusUpdate.InstallState state: Int): Boolean {
                return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED
            }
        }

        val listener = ModuleInstallProgressListener()

        moduleInstallClient
            .areModulesAvailable(scanner)
            .addOnSuccessListener {
                if (it.areModulesAvailable()) {
                    // Modules are present on the device
                    isModuleAvailable = true

                } else {
                    // Modules are not present on the device
                    val gmsBarCodeScannerInstallRequest = ModuleInstallRequest
                        .newBuilder()
                        .addApi(scanner)
                        .setListener(listener)
                        .build()

                    moduleInstallClient
                        .installModules(gmsBarCodeScannerInstallRequest)
                        .addOnSuccessListener { task ->
                            if (task.areModulesAlreadyInstalled()) {
                                // Modules are available
                                isModuleAvailable = true
                            }
                        }
                        .addOnFailureListener {
                            // Could not install Modules
                            onBoardViewModel.navigateToPreviousScreen()
                        }

                }
            }
            .addOnFailureListener {
                // Handle failure
                onBoardViewModel.navigateToPreviousScreen()
            }

        if (isModuleAvailable) {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    val senderId: String? = barcode.rawValue

                    if (senderId != null) {

                        val db = onBoardViewModel.firebaseFirestore
                        val currentUser = onBoardViewModel.firebaseAuth.currentUser?.uid
                        currentUser?.let {
                            val documentRef = db.collection("logs").document(it)

                            documentRef.get()
                                .addOnSuccessListener { document ->
                                    if (!document.exists() || (document.get("sender") == null)) {
                                        val data = hashMapOf(
                                            "array" to listOf<Any>(),
                                            "sender" to senderId,
                                            "ready" to false,
                                        )

                                        documentRef.set(data)
                                            .addOnSuccessListener { task ->
                                                onBoardViewModel.navigateToNextScreen()
                                            }
                                            .addOnFailureListener { exception ->
                                                // Handle failure
                                                println("Error writing document: ${exception.message}")
                                                onBoardViewModel.navigateToPreviousScreen()
                                            }
                                    } else {
                                        onBoardViewModel.navigateToPreviousScreen()
                                    }
                                }
                                .addOnFailureListener {
                                    onBoardViewModel.navigateToPreviousScreen()
                                }
                        }

                    }
                }
                .addOnCanceledListener {
                    onBoardViewModel.navigateToPreviousScreen()
                }
                .addOnFailureListener {
                    println("Scanner failed: ${it.message}")
                    onBoardViewModel.navigateToPreviousScreen()
                }
        } else {
            CircularProgressIndicator(progress = { downloadProgress / 100f })
            Spacer(modifier = Modifier.weight(0.2f))
            Text(
                text = "$downloadProgress %",
                style = MaterialTheme.typography.labelLarge
            )
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