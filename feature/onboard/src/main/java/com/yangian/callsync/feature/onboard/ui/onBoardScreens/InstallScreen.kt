package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.icon.CallMadeIcon
import com.yangian.callsync.core.designsystem.icon.ShareIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun InstallScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.weight(1f))

        // Call Sync Card
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.call_sync_name),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    Box {
                        Image(
                            painter = painterResource(R.mipmap.call_sync_launcher_background),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier
                                .size(48.dp)
                        )

                        Image(
                            painter = painterResource(R.mipmap.call_sync_launcher_foreground),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier
                                .size(48.dp)
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.install_call_sync_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Companion.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(0.8f)
        )

        // Num Sum Card
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.num_sum_name),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    Box {
                        Image(
                            painter = painterResource(R.mipmap.call_sync_launcher_background),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                        )

                        Image(
                            painter = painterResource(R.mipmap.num_sum_launcher_foreground),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.install_num_sum_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Companion.Center,
                    modifier = Modifier.padding(16.dp)
                )


                AssistChip(
                    onClick = { /*TODO*/ },
                    label = {
                        Text(
                            text = stringResource(id = R.string.download_num_sum)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            CallMadeIcon,
                            stringResource(id = R.string.download_num_sum)
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )

                AssistChip(
                    onClick = { /*TODO*/ },
                    label = {
                        Text(
                            text = stringResource(id = R.string.share_num_sum_app_link)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            ShareIcon,
                            stringResource(id = R.string.share_num_sum_app_link),
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        leadingIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(
    apiLevel = 34,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
private fun InstallScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            InstallScreen(modifier = Modifier.padding(16.dp))
        }
    }
}