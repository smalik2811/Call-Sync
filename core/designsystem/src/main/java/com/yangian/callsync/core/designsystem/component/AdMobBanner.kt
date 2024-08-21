package com.yangian.callsync.core.designsystem.component

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier
) {
//    BuildConfig.adUnitId
    AndroidView(
        modifier = modifier,
        factory = {
            AdView(it).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "TODO: Replace with your ad unit ID"
                loadAd(
                    AdRequest.Builder().addNetworkExtrasBundle(
                        AdMobAdapter::class.java,
                        Bundle().apply { putString("Collapsible", "bottom") }).build()
                )
            }
        }
    )
}