package com.yangian.callsync.core.designsystem.component

import android.content.Intent
import android.net.Uri
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.constant.Constant.FOREIGN_APP_DOWNLOAD_URL
import com.yangian.callsync.core.designsystem.R
import com.yangian.callsync.core.designsystem.icon.CallMadeIcon
import com.yangian.callsync.core.designsystem.icon.ShareIcon
import com.yangian.callsync.core.designsystem.theme.AppTheme

@Composable
fun MiniAppInstallCard(
    @DrawableRes foregroundResource: Int,
    @StringRes appName: Int,
    @StringRes installDesc: Int,
    titleStyle: TextStyle,
    descStyle: TextStyle,
    launcherIconWidthFactor: Float,
    @DimenRes globalPadding: Int,
    @DimenRes textPadding: Int,
    foreignApp: Boolean = false,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val message = stringResource(R.string.download_message, FOREIGN_APP_DOWNLOAD_URL)

    OutlinedCard(
        modifier = modifier
//            .wrapContentSize()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(globalPadding)),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(textPadding)),
                modifier = Modifier
            ) {
                LauncherIcon(
                    foregroundResource = foregroundResource,
                    modifier = Modifier.fillMaxWidth(launcherIconWidthFactor)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(textPadding)),
                ) {
                    Text(
                        text = stringResource(appName),
                        style = titleStyle
                    )

                    Text(
                        text = stringResource(installDesc),
                        style = descStyle,
                        maxLines = 3,
                    )
                }
            }

            if (foreignApp) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(FOREIGN_APP_DOWNLOAD_URL)
                            )
                            context.startActivity(intent, null)
                        },
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                CallMadeIcon,
                                stringResource(id = R.string.download_page)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = stringResource(R.string.download_page))
                        }
                    }

                    Spacer(modifier = Modifier.width(dimensionResource(globalPadding)))

                    Button(
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
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                ShareIcon,
                                stringResource(id = R.string.share_app_link),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.share_app_link)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HostAppMiniAppInstallCard(
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    descStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    launcherIconWidthFactor: Float = 0.3f,
    globalPadding: Int = R.dimen.padding_medium,
    textPadding: Int = R.dimen.padding_small,
    modifier: Modifier = Modifier
) {
    MiniAppInstallCard(
        foregroundResource = R.mipmap.app_launcher_foreground,
        appName = R.string.app_name,
        installDesc = R.string.install_desc_app,
        foreignApp = false,
        titleStyle = titleStyle,
        descStyle = descStyle,
        launcherIconWidthFactor = launcherIconWidthFactor,
        globalPadding = globalPadding,
        textPadding = textPadding,
        modifier = modifier
    )
}

@Composable
fun ForeignAppMiniAppInstallCard(
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    descStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    launcherIconFactor: Float = 0.3f,
    globalPadding: Int = R.dimen.padding_medium,
    textPadding: Int = R.dimen.padding_small,
    modifier: Modifier = Modifier
) {
    MiniAppInstallCard(
        foregroundResource = R.mipmap.foreign_app_launcher_foreground,
        appName = R.string.foreign_app_name,
        installDesc = R.string.install_desc_foreign_app,
        foreignApp = true,
        titleStyle = titleStyle,
        descStyle = descStyle,
        launcherIconWidthFactor = launcherIconFactor,
        globalPadding = globalPadding,
        textPadding = textPadding,
        modifier = modifier
    )
}

//@Preview
@Composable
private fun MiniCardPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AppBackground {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                HostAppMiniAppInstallCard()

                ForeignAppMiniAppInstallCard()
            }
        }
    }
}

//@MultiDevicePreview
@Composable
private fun MiniAppInstallCardPreview() {
    AppTheme {
        AppBackground {
            MiniAppInstallCard(
                foregroundResource = R.mipmap.app_launcher_foreground,
                appName = R.string.app_name,
                installDesc = R.string.install_desc_app,
                foreignApp = false,
                titleStyle = MaterialTheme.typography.headlineMedium,
                descStyle = MaterialTheme.typography.bodyMedium,
                globalPadding = R.dimen.padding_medium,
                textPadding = R.dimen.padding_small,
                launcherIconWidthFactor = 0.3f,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun AppInstallCard(
    @DrawableRes foregroundResource: Int,
    @StringRes appName: Int,
    @StringRes installDesc: Int,
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    descStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    showChips: Boolean = false,
    launcherIconFactor: Float = 0.3f,
    cardWidth: Dp,
    cardHeight: Dp,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val message = stringResource(R.string.download_message, FOREIGN_APP_DOWNLOAD_URL)

    OutlinedCard(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LauncherIconBox(
                    maxDimensionFactor = launcherIconFactor,
                    foregroundResource = foregroundResource
                )

                Text(
                    text = stringResource(appName),
                    style = titleStyle
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(installDesc),
                    style = descStyle,
                    textAlign = TextAlign.Center
                )
            }

            if (showChips) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    AssistChip(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(FOREIGN_APP_DOWNLOAD_URL)
                            )
                            context.startActivity(intent, null)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.download_page),
                            )
                        },
                        leadingIcon = {
                            Icon(
                                CallMadeIcon,
                                stringResource(id = R.string.download_page)
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
                                text = stringResource(id = R.string.share_app_link)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                ShareIcon,
                                stringResource(id = R.string.share_app_link),
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HostAppInstallCard(
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    descStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    cardWidth: Dp,
    cardHeight: Dp,
    launcherIconFactor: Float = 0.3f,
    modifier: Modifier = Modifier
) {
    AppInstallCard(
        foregroundResource = R.mipmap.app_launcher_foreground,
        appName = R.string.app_name,
        installDesc = R.string.install_desc_app,
        titleStyle = titleStyle,
        descStyle = descStyle,
        showChips = false,
        launcherIconFactor = launcherIconFactor,
        cardWidth = cardWidth,
        cardHeight = cardHeight,
        modifier = modifier
    )
}

@Composable
fun ForeignAppInstallCard(
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    descStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    cardWidth: Dp,
    cardHeight: Dp,
    launcherIconFactor: Float = 0.3f,
    modifier: Modifier = Modifier
) {
    AppInstallCard(
        foregroundResource = R.mipmap.foreign_app_launcher_foreground,
        appName = R.string.foreign_app_name,
        installDesc = R.string.install_desc_foreign_app,
        titleStyle = titleStyle,
        descStyle = descStyle,
        showChips = true,
        launcherIconFactor = launcherIconFactor,
        cardWidth = cardWidth,
        cardHeight = cardHeight,
        modifier = modifier
    )
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
private fun AppInstallCardPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AppBackground {
            BoxWithConstraints {
                val verticalArrangement = true
                val cardDimensionFactor = 0.4f
                val cardSize = when (verticalArrangement) {
                    true -> (maxHeight.value * cardDimensionFactor).dp
                    else -> (maxWidth.value * cardDimensionFactor).dp
                }

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    HostAppInstallCard(
                        launcherIconFactor = 0.5f,
                        cardWidth = cardSize,
                        cardHeight = cardSize * 0.8f,
                        modifier = Modifier
                    )

                    ForeignAppInstallCard(
                        launcherIconFactor = 0.5f,
                        cardWidth = cardSize,
                        cardHeight = cardSize,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}