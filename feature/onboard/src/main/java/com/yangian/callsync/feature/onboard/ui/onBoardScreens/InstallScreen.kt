package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.yangian.callsync.core.constant.Constant.NUM_SUM_DOWNLOAD_URL
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.icon.CallMadeIcon
import com.yangian.callsync.core.designsystem.icon.ShareIcon
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CompactInstallScreen(
    context: Context,
    message: String,
) {
    // Call Sync Card
    Card(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    dimensionResource(
                        R.dimen.corner_radius_extra_large
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.call_sync_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = Modifier
                        .sizeIn(
                            maxHeight = 100.dp,
                            maxWidth = 100.dp
                        )
                        .shadow(
                            elevation = dimensionResource(R.dimen.elevation_level_2),
                            shape = RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(R.mipmap.launcher_background),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                    )

                    Image(
                        painter = painterResource(R.mipmap.call_sync_launcher_foreground),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    dimensionResource(
                                        R.dimen.corner_radius_extra_large
                                    )
                                )
                            )
                    )
                }
            }

            Text(
                text = stringResource(R.string.install_call_sync_desc),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Companion.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }

    Spacer(
        modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large))
    )

    // Num Sum Card
    Card(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    dimensionResource(
                        R.dimen.corner_radius_extra_large
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.num_sum_name),
                    style = MaterialTheme.typography.titleLarge
                )

                Box(
                    modifier = Modifier
                        .sizeIn(
                            maxHeight = 100.dp,
                            maxWidth = 100.dp
                        )
                        .shadow(
                            elevation = dimensionResource(R.dimen.elevation_level_2),
                            shape = RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(R.mipmap.launcher_background),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                    )

                    Image(
                        painter = painterResource(R.mipmap.num_sum_launcher_foreground),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                    )
                }
            }

            Text(
                text = stringResource(R.string.install_num_sum_desc),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Companion.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            ) {
                AssistChip(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(NUM_SUM_DOWNLOAD_URL)
                        )
                        context.startActivity(intent, null)
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.download_num_sum),
                        )
                    },
                    leadingIcon = {
                        Icon(
                            CallMadeIcon,
                            stringResource(id = R.string.download_num_sum)
                        )
                    },
                )

                AssistChip(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, message)
                        context.startActivity(
                            Intent.createChooser(
                                intent,
                                context.getString(R.string.share_via)
                            ), null
                        )
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
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RowScope.ExpandedInstallScreen(
    context: Context,
    message: String,
) {
    // Call Sync Card
    Card(
        modifier = Modifier
            .weight(1f, true)
            .sizeIn(
                maxHeight = 400.dp
            )
            .clip(
                RoundedCornerShape(
                    dimensionResource(
                        R.dimen.corner_radius_extra_large
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.call_sync_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = Modifier
                        .sizeIn(
                            maxHeight = 200.dp,
                            maxWidth = 200.dp
                        )
                        .shadow(
                            elevation = dimensionResource(R.dimen.elevation_level_2),
                            shape = RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(R.mipmap.launcher_background),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                    )

                    Image(
                        painter = painterResource(R.mipmap.call_sync_launcher_foreground),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    dimensionResource(
                                        R.dimen.corner_radius_extra_large
                                    )
                                )
                            )
                    )
                }
            }

            Text(
                text = stringResource(R.string.install_call_sync_desc),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Companion.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }

    Spacer(
        modifier = Modifier.width(dimensionResource(R.dimen.padding_extra_large))
    )

    // Num Sum Card
    Card(
        modifier = Modifier
            .weight(1f, true)
            .sizeIn(maxHeight = 400.dp)
            .clip(
                RoundedCornerShape(
                    dimensionResource(
                        R.dimen.corner_radius_extra_large
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.num_sum_name),
                    style = MaterialTheme.typography.titleLarge
                )

                Box(
                    modifier = Modifier
                        .sizeIn(
                            maxHeight = 200.dp,
                            maxWidth = 200.dp
                        )
                        .shadow(
                            elevation = dimensionResource(R.dimen.elevation_level_2),
                            shape = RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                ) {
                    Image(
                        painter = painterResource(R.mipmap.launcher_background),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                    )

                    Image(
                        painter = painterResource(R.mipmap.num_sum_launcher_foreground),
                        contentDescription = stringResource(R.string.call_sync_app_logo),
                        modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(
                                    R.dimen.corner_radius_extra_large
                                )
                            )
                        )
                    )
                }
            }

            Text(
                text = stringResource(R.string.install_num_sum_desc),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Companion.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            ) {
                AssistChip(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(NUM_SUM_DOWNLOAD_URL)
                        )
                        context.startActivity(intent, null)
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.download_num_sum),
                        )
                    },
                    leadingIcon = {
                        Icon(
                            CallMadeIcon,
                            stringResource(id = R.string.download_num_sum)
                        )
                    },
                )

                AssistChip(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, message)
                        context.startActivity(
                            Intent.createChooser(
                                intent,
                                context.getString(R.string.share_via)
                            ), null
                        )
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
                    }
                )
            }
        }
    }
}

@Composable
fun InstallScreen(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val context = LocalContext.current
    val message = stringResource(R.string.num_sum_download_message, NUM_SUM_DOWNLOAD_URL)

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.EXPANDED -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(scaffoldPadding)
                    .windowInsetsPadding(WindowInsets.safeContent)
            ) {
                ExpandedInstallScreen(
                    context = context,
                    message = message,
                )
            }
        }

        else -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                CompactInstallScreen(
                    context = context,
                    message = message,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CompactInstallScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                InstallScreen(
                    PaddingValues(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun ExpandedInstallScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            Column {
                InstallScreen(
                    PaddingValues(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}
