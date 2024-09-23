package com.yangian.callsync.core.designsystem

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.content.ContextCompat

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.openPermissionSetting() {
    Intent(ACTION_APPLICATION_DETAILS_SETTINGS).also {
        val uri: Uri = Uri.fromParts("package", packageName, null)
        it.data = uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(it)
    }
}