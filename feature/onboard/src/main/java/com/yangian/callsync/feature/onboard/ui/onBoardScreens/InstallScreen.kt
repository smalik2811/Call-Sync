package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.yangian.callsync.core.constant.Constant.NUM_SUM_DOWNLOAD_URL
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.icon.CallMadeIcon
import com.yangian.callsync.core.designsystem.icon.ShareIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InstallScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val message = stringResource(R.string.num_sum_download_message, NUM_SUM_DOWNLOAD_URL)

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // Call Sync Card
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
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

                    Box(modifier = Modifier.clip(shape = MaterialTheme.shapes.extraSmall)) {
                        Image(
                            painter = painterResource(R.mipmap.launcher_background),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_large))
                        )

                        Image(
                            painter = painterResource(R.mipmap.call_sync_launcher_foreground),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.icon_size_large))
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

        // Num Sum Card
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
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

                    Box(modifier = Modifier.clip(shape = MaterialTheme.shapes.extraSmall)) {
                        Image(
                            painter = painterResource(R.mipmap.launcher_background),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_large))
                        )

                        Image(
                            painter = painterResource(R.mipmap.num_sum_launcher_foreground),
                            contentDescription = stringResource(R.string.call_sync_app_logo),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_large))
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.install_num_sum_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Companion.Center,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )


                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_tiny)),
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_tiny))
                ) {
                    AssistChip(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(NUM_SUM_DOWNLOAD_URL)
                            )
                            startActivity(context, intent, null)
                        },
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
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, message)
                            startActivity(context, Intent.createChooser(intent,
                                context.getString(R.string.share_via)), null)
                        },
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
        }
    }
}

@Preview(
    apiLevel = 34,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    device = "spec:parent=pixel_5"
)
@Composable
private fun InstallScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            InstallScreen(modifier = Modifier.padding(16.dp))
        }
    }
}