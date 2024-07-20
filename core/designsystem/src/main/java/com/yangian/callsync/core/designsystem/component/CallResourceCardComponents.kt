package com.yangian.callsync.core.designsystem.component

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.R
import com.yangian.callsync.core.designsystem.icon.AccountCircleIcon
import com.yangian.callsync.core.designsystem.icon.CallIcon
import com.yangian.callsync.core.designsystem.icon.CallMadeIcon
import com.yangian.callsync.core.designsystem.icon.CallMissedIcon
import com.yangian.callsync.core.designsystem.icon.CallReceivedIcon
import com.yangian.callsync.core.designsystem.icon.ChatIcon
import com.yangian.callsync.core.designsystem.icon.HistoryIcon
import com.yangian.callsync.core.designsystem.icon.PhoneDisabledIcon
import com.yangian.callsync.core.designsystem.icon.PhoneMissedIcon
import com.yangian.callsync.core.designsystem.icon.VoicemailIcon
import com.yangian.callsync.core.designsystem.icon.WifiCallingIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.designsystem.theme.ContactAvatarColorSchemeDark
import com.yangian.callsync.core.designsystem.theme.ContactAvatarColorThemeLight
import com.yangian.callsync.core.designsystem.theme.extendedDark
import com.yangian.callsync.core.designsystem.theme.extendedLight
import com.yangian.callsync.core.model.CallResource
import java.util.Locale

@Composable
fun CallResourceFlatItem(
    callResource: CallResource,
    listItemColors: ListItemColors = ListItemDefaults.colors(),
    onCallResourceItemClick: (Long) -> Unit = {},
    onCallClick: () -> Unit,
) {

    val avatarColor = if (isSystemInDarkTheme()) {
        ContactAvatarColorSchemeDark().colors[callResource.number.last().digitToInt() % 14]
    } else {
        ContactAvatarColorThemeLight().colors[callResource.number.last().digitToInt() % 14]
    }

    ListItem(
        headlineContent = {
            if (callResource.name.isNullOrEmpty()) {
                Text(
                    text = PhoneNumberUtils.formatNumber(
                        callResource.number,
                        Locale.getDefault().country
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            } else {
                Text(
                    text = "${callResource.name}",
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }

        },
        supportingContent = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (callResource.type) {
                    1 -> Icon(
                        imageVector = CallReceivedIcon,
                        contentDescription = "Call received",
                        tint = when (isSystemInDarkTheme()) {
                            true -> extendedDark.success.color
                            false -> extendedLight.success.color
                        },
                        modifier = Modifier
                            .size(18.dp)
                    )

                    2 -> Icon(
                        imageVector = CallMadeIcon,
                        contentDescription = "Call made",
                        tint = when (isSystemInDarkTheme()) {
                            true -> extendedDark.success.color
                            false -> extendedLight.success.color
                        },
                        modifier = Modifier
                            .size(18.dp)
                    )

                    3 -> Icon(
                        imageVector = CallMissedIcon,
                        contentDescription = "Call missed",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(18.dp)
                    )

                    4 -> Icon(
                        imageVector = VoicemailIcon,
                        contentDescription = "Voicemail",
                        tint = when (isSystemInDarkTheme()) {
                            true -> extendedDark.success.color
                            false -> extendedLight.success.color
                        },
                        modifier = Modifier
                            .size(18.dp)
                    )

                    5 -> Icon(
                        imageVector = PhoneMissedIcon,
                        contentDescription = "Call rejected",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(18.dp)
                    )

                    6 -> Icon(
                        imageVector = PhoneDisabledIcon,
                        contentDescription = "Call blocked",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(18.dp)
                    )

                    7 -> Icon(
                        imageVector = WifiCallingIcon,
                        contentDescription = "Answered on another device",
                        tint = when (isSystemInDarkTheme()) {
                            true -> extendedDark.success.color
                            false -> extendedLight.success.color
                        },
                        modifier = Modifier
                            .size(18.dp)
                    )
                }

                Text(
                    text = callResource.getDateString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (callResource.type in listOf(3, 5, 6)) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
        },
        leadingContent = {
            Image(
                imageVector = AccountCircleIcon,
                contentDescription = stringResource(R.string.contact_avatar),
                modifier = Modifier
                    .size(
                        42.dp
                    ),
                colorFilter = ColorFilter.tint(
                    Color(
                        android.graphics.Color.parseColor(avatarColor)
                    ),
                ),
            )
        },
        trailingContent = {
            IconButton(
                onClick = {
                    onCallClick()
                }
            ) {
                Icon(
                    imageVector = CallIcon,
                    contentDescription = "Make Call",
                )
            }
        },
        colors = listItemColors,
        modifier = Modifier.clickable {
            onCallResourceItemClick(callResource.id)
        }
    )
}

@Composable
fun CallResourceFilledCardItem(
    callResource: CallResource,
    modifier: Modifier = Modifier,
    onCallResourceItemClick: (Long) -> Unit,
    onSMSClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onCallHistoryClick: () -> Unit = {},
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(20),
        modifier = Modifier.padding(8.dp)
    ) {
        Column {
            CallResourceFlatItem(
                callResource = callResource,
                listItemColors = ListItemColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    headlineColor = MaterialTheme.colorScheme.onSurface,
                    leadingIconColor = MaterialTheme.colorScheme.onSurface,
                    overlineColor = MaterialTheme.colorScheme.onSurface,
                    supportingTextColor = MaterialTheme.colorScheme.onSurface,
                    trailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledHeadlineColor = CardDefaults.cardColors().disabledContentColor,
                    disabledLeadingIconColor = CardDefaults.cardColors().disabledContentColor,
                    disabledTrailingIconColor = CardDefaults.cardColors().disabledContentColor
                ),
                onCallResourceItemClick,
                onCallClick
            )

            HorizontalDivider()

            val localContext = LocalContext.current

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceEvenly,
//                    modifier = Modifier.clickable {
//                    }
//                ) {
//
//                    Icon(
//                        imageVector = VideocamIcon,
//                        contentDescription = "Video Call",
//                    )
//
//                    Text(
//                        text = "Video Call",
//                        style = MaterialTheme.typography.bodySmall,
//                    )
//                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.clickable {
                        onSMSClick()
                    }
                ) {

                    Icon(
                        imageVector = ChatIcon,
                        contentDescription = "Message",
                    )

                    Text(
                        text = "Message",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.clickable{
                        onCallHistoryClick()
                    }
                ) {

                    Icon(
                        imageVector = HistoryIcon,
                        contentDescription = "History",
                    )

                    Text(
                        text = "History",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

            }
        }
    }
}

@Preview()
@Composable
private fun CallResourceFlatItemPreview() {
    val context = LocalContext.current
    CallSyncAppTheme {
        CallSyncAppBackground {
            CallResourceFlatItem(
                callResource = CallResource(
                    id = 5,
                    name = "SONU Malik",
                    number = "+919876453210",
                    timestamp = (1546300800000L..1717027200000L).random(),
                    duration = (0L..86400L).random(),
                    type = (1..7).random()
                ),
                onCallResourceItemClick = {},
                onCallClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun CallResourceFilledCardItemPreview() {
    val context = LocalContext.current
    CallSyncAppTheme {
        CallSyncAppBackground {
            CallResourceFilledCardItem(
                callResource = CallResource(
                    id = 5,
                    name = "SONU Malik",
                    number = "+919876453210",
                    timestamp = (1546300800000L..1717027200000L).random(),
                    duration = (0L..86400L).random(),
                    type = (1..7).random()
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(4.dp),
                {},
                {},
                {},
                {}
            )
        }
    }
}