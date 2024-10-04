package com.yangian.callsync.core.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.Wallpapers
import com.google.android.gms.ads.nativead.NativeAd
import com.yangian.callsync.core.designsystem.component.CallResourceFlatItem
import com.yangian.callsync.core.designsystem.component.admob.CallNativeAd
import com.yangian.callsync.core.designsystem.icon.CallIcon
import com.yangian.callsync.core.designsystem.icon.ChatIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.model.CallResource

@Composable
fun CallResourceListItem(
    callResource: CallResource,
    focussedCallResourceId: Long,
    onCallResourceItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onSMSClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    nativeAd: NativeAd?
) {
    val isFocused: Boolean = focussedCallResourceId == callResource.id
    val cardElevation by animateDpAsState(
        targetValue = if (isFocused) {
            dimensionResource(R.dimen.elevation_level_1)
        } else {
            dimensionResource(R.dimen.elevation_level_0)
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ),
        label = "cardElevation"
    )

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        shape = RoundedCornerShape(
            dimensionResource(
                com.yangian.callsync.core.designsystem.R.dimen.corner_radius_medium
            )
        ),
        modifier = modifier
            .padding(
                start = dimensionResource(
                    com.yangian.callsync.core.designsystem.R.dimen.padding_tiny
                ),
                end = dimensionResource(
                    com.yangian.callsync.core.designsystem.R.dimen.padding_tiny
                )
            )
//            .wrapContentHeight()
            .clickable {
                onCallResourceItemClick(callResource.id)
            }
    ) {
        Column {
            CallResourceFlatItem(
                callResource = callResource,
                isFocused = isFocused,
                onCallResourceItemClick = onCallResourceItemClick,
            )

            AnimatedVisibility(
                visible = focussedCallResourceId == callResource.id,
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(
                    animationSpec = spring(
                        stiffness = Spring
                            .StiffnessLow,
                        dampingRatio = Spring
                            .DampingRatioMediumBouncy

                    )
                ),
                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(
                    animationSpec = spring(
                        stiffness = Spring
                            .StiffnessLow,
                        dampingRatio = Spring
                            .DampingRatioMediumBouncy
                    )
                )
            ) {
                Column {
                    HorizontalDivider()

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                //                            .fillMaxHeight()
                                .padding(
                                    top = dimensionResource(R.dimen.padding_medium),
                                    bottom = dimensionResource(R.dimen.padding_medium)
                                )
                                .clickable(onClickLabel = stringResource(R.string.make_call)) {
                                    onCallClick()
                                }
                        ) {

                            Icon(
                                imageVector = CallIcon,
                                contentDescription = stringResource(R.string.call),
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(dimensionResource(R.dimen.padding_tiny))
                                    .width(dimensionResource(R.dimen.padding_extra_large))
                            )
                            Text(
                                text = stringResource(R.string.call),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                //                            .fillMaxHeight()
                                .padding(
                                    top = dimensionResource(R.dimen.padding_medium),
                                    bottom = dimensionResource(R.dimen.padding_medium)
                                )
                                .clickable {
                                    onSMSClick()
                                }
                        ) {

                            Icon(
                                imageVector = ChatIcon,
                                contentDescription = stringResource(R.string.message),
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(dimensionResource(R.dimen.padding_tiny))
                                    .width(dimensionResource(R.dimen.padding_extra_large))
                            )
                            Text(
                                text = stringResource(R.string.message),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    nativeAd?.let {
                        CallNativeAd(nativeAd)
                    }
                }
            }
        }
    }
}

@Preview(
    device = "id:pixel_8_pro",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    locale = "en-in"
)
@Composable
private fun CallResourceListItemPreview(
    @PreviewParameter(CallResourcePreviewParameterProvider::class) callResourceList: List<CallResource>
) {
    CallSyncAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_tiny))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(com.yangian.callsync.core.designsystem.R.dimen.padding_tiny))
            ) {
                for (callResource in callResourceList) {
                    CallResourceListItem(
                        callResource = callResource,
                        focussedCallResourceId = 2L,
                        {},
                        nativeAd = null
                    )
                }
            }
        }
    }
}