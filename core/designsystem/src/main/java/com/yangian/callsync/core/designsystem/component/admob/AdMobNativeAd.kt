package com.yangian.callsync.core.designsystem.component.admob

import android.content.Context
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdOptions.ADCHOICES_TOP_RIGHT
import com.google.android.gms.ads.nativead.NativeAdView
import com.yangian.callsync.core.designsystem.R
import com.yangian.callsync.core.designsystem.component.AppBackground
import com.yangian.callsync.core.designsystem.theme.AppTheme


@Composable
private fun LoadAdContent(nativeAd: NativeAd?, composeView: View) {

    nativeAd?.let { ad ->
        ListItem(
            overlineContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${ad.headline}",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            },
            headlineContent = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.ad_badge),
                        contentDescription = "Ad badge",
                        modifier = Modifier
                            .size(24.dp)
                    )

                    if (ad.starRating != null) {
                        StarRating(
                            rating = ad.starRating!!,
                            modifier = Modifier.wrapContentSize()
                        )
                    } else {
                        Text(
                            text = ad.store ?: ad.advertiser ?: "",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },
            supportingContent = {

                Row {
                    OutlinedButton(
                        onClick = {
                            composeView.performClick()
                        },
                        contentPadding = PaddingValues(
                            top = 0.dp,
                            bottom = 0.dp,
                            start = 12.dp,
                            end = 12.dp
                        ),
                        modifier = Modifier
                            .padding(top = 2.dp, bottom = 2.dp)
                            .height(24.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${ad.callToAction}",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            },
            leadingContent = {
                Image(
                    painter = rememberAsyncImagePainter(model = ad.icon?.drawable),
                    contentDescription = ad.advertiser,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.icon_size_large))
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    } ?: run {
        // Placeholder for loading state or error state
        Text("Loading ad...")
    }
}

@Composable
fun NativeAdView(
    ad: NativeAd,
    adContent: @Composable (ad: NativeAd, contentView: View) -> Unit,
) {
    val contentViewId by remember { mutableIntStateOf(View.generateViewId()) }
    val adViewId by remember { mutableIntStateOf(View.generateViewId()) }
    AndroidView(
        factory = { context ->
            val contentView = ComposeView(context).apply {
                id = contentViewId
            }
            NativeAdView(context).apply {
                id = adViewId
                addView(contentView)
            }
        },
        update = { view ->
            val adView = view.findViewById<NativeAdView>(adViewId)
            val contentView = view.findViewById<ComposeView>(contentViewId)

            adView.setNativeAd(ad)
            adView.callToActionView = contentView
            contentView.setContent { adContent(ad, contentView) }
        }
    )
}

@Composable
fun CallNativeAd(nativeAd: NativeAd) {
    NativeAdView(ad = nativeAd) { ad, view ->
        LoadAdContent(ad, view)
    }
}

fun loadNativeAd(context: Context, adUnitId: String, callback: (NativeAd?) -> Unit) {
    val builder = AdLoader.Builder(context, adUnitId)
        .forNativeAd { nativeAd ->
            callback(nativeAd)
        }

    val adLoader = builder
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                callback(null)
            }
        })
        .withNativeAdOptions(
            NativeAdOptions
                .Builder()
                .setRequestCustomMuteThisAd(false)
                .setAdChoicesPlacement(ADCHOICES_TOP_RIGHT)
                .build()
        )
        .build()

    adLoader.loadAd(AdRequest.Builder().build())
}

@Composable
fun StarRating(
    rating: Double,
    maxRating: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = when {
                    i < rating.toInt() -> Icons.Filled.StarRate
                    rating.toInt() == i && i < rating -> Icons.AutoMirrored.Filled.StarHalf
                    else -> Icons.Outlined.StarOutline
                },
                contentDescription = "Star $i",
                tint = if (i <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(device = "id:Nexus S")
@Composable
private fun StarRatingPreview() {
    AppTheme {
        AppBackground {
            StarRating(3.2, 5)
        }
    }
}