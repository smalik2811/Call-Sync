package com.yangian.callsync.core.designsystem.component

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yangian.callsync.core.designsystem.R
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.network.model.DkmaManufacturer

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DkmaScreenWebViewCard(
    headingText: String,
    webViewHtmlContent: String,
    isVisible: Boolean,
    alterVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    val prefixHTMLCode: String = "<head>" +
            "        <meta charset=\"utf-8\">" +
            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.04\">" +
            "        <link rel=\"stylesheet\" href=\"https://dontkillmyapp.com/assets/main.css\">" +
            "        <link rel=\"stylesheet\" href=\"https://dontkillmyapp.com/assets/css/style.css\">" +
            "        <script src=\"https://dontkillmyapp.com/assets/js/findAndReplaceDOMText.js\"></script>" +
            "        <script src=\"https://dontkillmyapp.com/assets/js/main.js\"></script>" +
            "        <style>" +
            "            body {" +
            "                padding: 8px;" +
            "            }" +
            "        </style>" +
            "    </head>" +
            "    <body>"
    val suffixHTMLCode: String = "</body>" +
            "        <script>" +
            "            findAndReplaceDOMText(document.body, {" +
            "                find: \"your app\"," +
            "                replace: \"${stringResource(R.string.app_name)}\"" +
            "            });" +
            "        </script>"

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
        shape = ShapeDefaults.ExtraLarge
    ) {
        Column(
            modifier = Modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp)
                    .clickable { alterVisibility() },
            ) {

                Text(
                    text = headingText,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(0.8f),
                )

                Spacer(
                    modifier = Modifier.weight(0.02f)
                )

                Icon(
                    imageVector = when (isVisible) {
                        false -> Icons.Filled.ExpandMore
                        true -> Icons.Filled.ExpandLess
                    },
                    contentDescription = "",
                    modifier = Modifier
                        .weight(0.1f),
                )
            }

            AnimatedVisibility(isVisible) {

                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true

                            loadData(
                                prefixHTMLCode + webViewHtmlContent + suffixHTMLCode,
                                "text/html",
                                "Utf-8"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DkmaView(
    dkmaManufacturer: DkmaManufacturer,
    isIssueVisible: Boolean,
    isSolutionVisible: Boolean,
    alterIssueVisibility: () -> Unit,
    alterSolutionVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = Modifier.height(16.dp)
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(8.dp).verticalScroll(rememberScrollState())
    ) {

        OutlinedCard {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "Potential Issues",
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 3,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Some Android Manufacturers prevent new apps from working in background. Check below if there are any issues caused by your manufacturer \uD83D\uDC47",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DkmaScreenWebViewCard("Check Issues", dkmaManufacturer.explanation, isIssueVisible, alterIssueVisibility)
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedCard {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "Potential Solutions",
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 3,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Check out some solutions posted by community to keep your app functioning as intended \uD83D\uDC47",
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DkmaScreenWebViewCard("Check Solutions", dkmaManufacturer.user_solution, isSolutionVisible, alterSolutionVisibility)
            }
        }
    }
}

@Preview(
    device = "id:pixel_7_pro", apiLevel = 33, showSystemUi = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
private fun DkmaViewPreview() {
    val mockData = DkmaManufacturer(
        explanation = "<div class=\"caution-box\">  8/2019 update  <br /><br />  <b>Good news</b>: HMD Global <a href=\"https://community.phones.nokia.com/discussion/51246/tapping-into-android-pies-adaptive-battery-for-optimum-battery-performance\">claims to disable Evenwell powersaving apps</a> on all devices running Android Pie or newer.  <br />  <b>NOT SO GOOD NEWS</b>: DuraSpeed remains.</div><p>HMD Global/Nokia was the main reason this website came to exist. They had the most aggressive app killers preinstalled on their phones.</p><p>There were three different app killing mechanisms:</p><ul>  <li><em>com.evenwell.powersaving.g3</em> on Android Pie for most Nokia phones - <strong>this one has been disabled since 8/2019 on devices running Pie or greater</strong></li>  <li><em>com.evenwell.emm</em> on Android Go (Oreo?) for Nokia 1 - <strong>probably still in the wild since HMD only disabled Evenwell apps for Pie or greater</strong></li>  <li><em>DuraSpeed</em> on Android Pie (build 00WW_3_180) for the US Nokia 3.1 (TA-1049, TA-1063) and Nokia 5.1 - <strong>this one is still in the wild</strong></li></ul><h3 id=\"most-nokia-phones-power-saver-aka-comevenwellpowersavingg3\">Most Nokia phones (Power saver AKA com.evenwell.powersaving.g3)</h3><div class=\"caution-box\">  The Evenwell Power saver *(com.evenwell.powersaving.g3)* has been disabled by HMD Global for devices running Pie or greater as of 8/2019.</div><p>The text below has been left here to preserve the detail and history of events.</p><p><del>Note: In Feb/March 2019, apparently on a few models distributed in Europe and US, the Evenwell Power Saver has been reworked to not kill the apps as aggressively, which largely resolves all issues for those models.</del></p><p><del>Nokia on Android O and P kills any background process including sleep tracking (or any other sport tracking) after 20 minutes if the screen is off. Also when killed all alarms are stopped which renders for example any alarm clock apps useless.</del></p><p><del>We have investigated this issue in details. We did even purchase a Nokia 6.1 to be able to reproduce the issue. The problem only occurs on Nokia devices with Android Pie. Nokia started to bundle a toxic app (package: com.evenwell.powersaving.g3 or com.evenwell.emm, name: Power saver) with their devices by some 3rd party company Evenwell. This app kills apps in the most brutal way we have seen so far among Android vendors.</del></p><p><del>Whitelisting apps from battery optimizations does not help! Evenwell kills even whitelisted apps.</del></p><p><del>What this non-standard app does is every process gets killed after 20 minutes regardless it is actually supposed to be running and doing a useful job for the user. Also alarms are not triggered. The aim is apparently to save your battery by rendering tracking apps and other apps that use background processing useless.</del></p><p><del>Moreover even third-party user visible alarms (alarm clock alarms) are not triggering properly on Nokia as foreground services cannot be started from background on Nokia. This is a serious issue unparalleled to any other vendor. We did not yet find a workaround for this :(. 3rd party alarms clock / calendars etc… won’t be realiable on Nokia.</del></p><p><del>You can read more on this issue here:<a href=\"https://community.phones.nokia.com/discussion/3428/background-service-killed-even-when-whitelisted\">https://community.phones.nokia.com/discussion/3428/background-service-killed-even-when-whitelisted</a></del></p><p>For fun investigative read about Evenwell, check out <a href=\"https://medium.com/@roundedeverett/who-is-nokia-cb24ecbc52a9\">Who is Nokia?</a></p><h3 id=\"nokia-1-comevenwellemm\">Nokia 1 (com.evenwell.emm)</h3><p>On Nokia 1 there is an alternative package that works very similar to what the com.evenwell.powersaving.g3 package is doing on the higher end models.</p><h3 id=\"nokia-31-and-51-duraspeed\">Nokia 3.1 and 5.1 (DuraSpeed)</h3><p>On Mediatek-based devices, HMD has baked in <a href=\"https://www.appbrain.com/app/duraspeed/com.mediatek.duraspeed\">DuraSpeed</a> as a system service. There is no user-facing control, or whitelist; this Mediatek-developed task killer terminates all background apps without prejudice.</p><p>DuraSpeed can be disabled through the global settings store, but this is a protected area of Android that can only be manipulated through adb, or an app that has been granted the <code class=\"language-plaintext highlighter-rouge\">WRITE_SECURE_SETTINGS</code> permission (which must also be done with ADB). Additionally, the setting does not survive a reboot. Users can fix their devices themselves using an automation app (see “Solution for users”), or apps can request the <code class=\"language-plaintext highlighter-rouge\">WRITE_SECURE_SETTINGS</code> permission and then cycle the flag on startup to kill DuraSpeed. Syncthing-Fork is one app that has <a href=\"https://github.com/Catfriend1/syncthing-android/wiki/Nokia-HMD-phone-preparations\">taken this approach</a>.</p><p>Unfortunately, there are <a href=\"https://forum.xda-developers.com/showpost.php?s=1f4fbd7602c2739781c1c5346bb06e36&amp;p=80157506&amp;postcount=7\">some</a> <a href=\"https://github.com/urbandroid-team/dont-kill-my-app/issues/57#issuecomment-534246709\">reports</a> that even this fix does not work.</p>",
        user_solution = "<h3 id=\"most-nokia-phones-power-saver-aka-comevenwellpowersavingg3\">Most Nokia phones (Power saver AKA com.evenwell.powersaving.g3)</h3><p>To fix this issue, please do the following:</p><ul>  <li>    <p>Go to <strong>Phone settings &gt; Apps &gt; See all apps</strong>.</p>  </li>  <li>    <p>Tap on the <strong>right top corner menu &gt; Show system</strong>.</p>  </li>  <li>    <p>Find <strong>Power saver</strong> app in the list, select it and <strong>Force close</strong>. It will remain stopped for a while, but will restart itself eventually.</p>  </li></ul><p>From now on, background apps should work normally and use the standard Android battery optimizations.</p><p>Still 3rd party alarm clocks or any task scheduling of foreground tasks at a particular time won’t work. <del>We do not have any solution for this at the moment</del> UPDATE: in our preliminary tests it seems that force stopping or uninstalling the <strong>Power saver</strong> app also fixes alarms and starting of foreground services, until the Power saver restarts.</p><p>Alternative solution for tech-savvy users:</p><h3 id=\"most-nokia-models\">Most Nokia models</h3><div class=\"caution-box\">  The Evenwell Power saver *(com.evenwell.powersaving.g3)* has been disabled by HMD Global for devices running Pie or greater as of 8/2019.</div><p>Disable the <em>com.evenwell.powersaving.g3</em> package via the following adb commands:</p><p><code class=\"language-plaintext highlighter-rouge\">adb shell</code><br /><code class=\"language-plaintext highlighter-rouge\">pm disable-user com.evenwell.powersaving.g3</code></p><h3 id=\"nokia-1-android-go\">Nokia 1 (Android Go)</h3><p>Disable the <em>com.evenwell.emm</em> package via the following adb commands:</p><p><code class=\"language-plaintext highlighter-rouge\">adb shell</code><br /><code class=\"language-plaintext highlighter-rouge\">pm disable-user com.evenwell.emm</code></p><h3 id=\"nokia-31-and-51\">Nokia 3.1 and 5.1</h3><p>Regrettably, HMD did not include any sort of Settings switch to control DuraSpeed’s operation. And since the task killer is a system service and not an app, it cannot simply be uninstalled. Fortunately, DuraSpeed does have a hidden kill switch: It watches the <code class=\"language-plaintext highlighter-rouge\">setting.duraspeed.enabled</code> setting and will stop itself when the flag is set to any value that does not equal <code class=\"language-plaintext highlighter-rouge\">1</code>. Once DuraSpeed stops itself, the phone is cured and all background apps will function normally. However, this workaround does not stick across reboots, so the flag has to be cycled at every boot using an automation app like <a href=\"https://play.google.com/store/apps/details?id=com.arlosoft.macrodroid\">MacroDroid</a>.</p><p>First, use adb to grant MacroDroid (or your choice of automation app) the ability to write to the global settings store:</p><div class=\"language-plaintext highlighter-rouge\"><div class=\"highlight\"><pre class=\"highlight\"><code>adb shell pm grant com.arlosoft.macrodroid android.permission.WRITE_SECURE_SETTINGS</code></pre></div></div><p>Then create a task, triggered at <strong>Device Boot</strong>, that performs the following:</p><ol>  <li>System Setting: type <strong>Global</strong>, name <strong>setting.duraspeed.enabled</strong>, value <strong>2</strong></li>  <li>System Setting: type <strong>System</strong>, name <strong>setting.duraspeed.enabled</strong>, value <strong>2</strong></li>  <li>System Setting: type <strong>Global</strong>, name <strong>setting.duraspeed.enabled</strong>, value <strong>0</strong></li>  <li>System Setting: type <strong>System</strong>, name <strong>setting.duraspeed.enabled</strong>, value <strong>0</strong></li></ol><p>NOTE: You need both ‘Global’ and ‘System’ type settings (the screenshots below show only Global - you get the idea).</p><div class=\"img-block\">  <figure>     <img src=\"https://dontkillmyapp.com/assets/img/nokia/duraspeed_macrodroid_kyrasantae.png\" />     <figcaption>MacroDroid example task</figcaption>  </figure>  <figure>     <img src=\"https://dontkillmyapp.com/assets/img/nokia/duraspeed_tasker_yoryan.jpg\" />     <figcaption>Tasker example task</figcaption>  </figure></div><p>Run this task and verify there are no errors. If all is well, then DuraSpeed will be immediately disabled, and it will also be disabled on reboot.</p>",
    )

    CallSyncAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            DkmaView(
                mockData,
                isIssueVisible = true,
                isSolutionVisible = true,
                alterIssueVisibility = {},
                alterSolutionVisibility = {},
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(28.0.dp))

            )
        }
    }
}