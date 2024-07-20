package com.yangian.callsync.core.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.component.CallResourceFilledCardItem
import com.yangian.callsync.core.designsystem.component.CallResourceFlatItem
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.model.CallResource

@Composable
fun CallResourceListItem(
    callResource: CallResource,
    focussedCallResourceId: Long,
    onCallResourceItemClick: (Long) -> Unit,
    onSMSClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onCallHistoryClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = callResource.id == focussedCallResourceId,
        enter = expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            animationSpec = tween(500)
        ),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top
        ) + fadeOut(
            animationSpec = tween(500)
        )
    ) {
        CallResourceFilledCardItem(
            callResource = callResource,
            onCallResourceItemClick = onCallResourceItemClick,
            onSMSClick = onSMSClick,
            onCallClick = onCallClick,
            onCallHistoryClick = onCallHistoryClick
        )
    }
    if (
        callResource.id != focussedCallResourceId
    ) {
        CallResourceFlatItem(
            callResource = callResource,
            onCallResourceItemClick = onCallResourceItemClick,
            onCallClick = onCallClick
        )
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