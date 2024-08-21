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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.component.CallResourceFlatItem
import com.yangian.callsync.core.designsystem.icon.CallIcon
import com.yangian.callsync.core.designsystem.icon.ChatIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.model.CallResource

@Composable
fun CallResourceListItem(
    callResource: CallResource,
    focussedCallResourceId: Long,
    onCallResourceItemClick: (Long) -> Unit,
    onSMSClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val cardElevation by animateDpAsState(
        targetValue = if (focussedCallResourceId == callResource.id) {
            1.dp
        } else {
            0.dp
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
        shape = RoundedCornerShape(20),
        modifier = Modifier
            .padding(start = 2.dp, end = 2.dp)
            .wrapContentHeight()
            .clickable {
                onCallResourceItemClick(callResource.id)
            }
    ) {
        Column {
            CallResourceFlatItem(
                callResource = callResource,
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
                            .fillMaxHeight()
                            .padding(top = 16.dp, bottom = 16.dp)
                            .clickable(onClickLabel = "Make Call") {
                            onCallClick()
                        }
                    ) {

                        Icon(
                            imageVector = CallIcon,
                            contentDescription = "Call",
                        )
                        Spacer(modifier = Modifier.height(2.dp).width(48.dp))
                        Text(
                            text = "Call",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 16.dp, bottom = 16.dp).clickable {
                            onSMSClick()
                        }
                    ) {

                        Icon(
                            imageVector = ChatIcon,
                            contentDescription = "Message",
                        )
                        Spacer(modifier = Modifier.height(2.dp).width(48.dp))
                        Text(
                            text = "Message",
                            style = MaterialTheme.typography.bodySmall,
                        )
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
private fun CallResourceListItemPreview() {
    CallSyncAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Column {
                for (i in 1..5) {
                    CallResourceListItem(
                        callResource = CallResource(
                            id = i.toLong(),
                            name = "Richard Khijkjoslksjljlkjs",
                            number = "+91${(1000000000..9999999999).random()}",
                            timestamp = (1546300800000L..1717027200000L).random(),
                            duration = (0L..86400L).random(),
                            type = (1..7).random()
                        ),
                        focussedCallResourceId = 2L,
                        {}
                    )
                }
            }
        }
    }
}