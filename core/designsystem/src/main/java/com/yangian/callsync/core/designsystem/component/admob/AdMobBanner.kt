package com.yangian.callsync.core.designsystem.component.admob

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.yangian.callsync.core.designsystem.BuildConfig

@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier
) {
    val bannerAdUnitId: String = BuildConfig.BannerAdUnitId

    AndroidView(
        modifier = modifier,
        factory = {
            AdView(it).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = bannerAdUnitId
                loadAd(
                    AdRequest.Builder().build()
                )
            }
        }
    )
}