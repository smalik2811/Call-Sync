package com.yangian.callsync.feature.onboard.ui.onBoardScreens

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.yangian.callsync.core.designsystem.component.CalculatorButton
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.GifImage
import com.yangian.callsync.core.designsystem.isPermissionGranted
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.feature.onboard.R

@Composable
fun UnlockScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.unlock_step_description),
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(
            modifier = Modifier.fillMaxHeight(0.01f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            CalculatorButton(
                text = stringResource(R.string.equal_sign),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_1),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_8),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_5),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_6),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.number_8),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = stringResource(R.string.equal_sign),
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )
        }

        Spacer(
            modifier = Modifier.fillMaxHeight(0.02f)
        )

        OutlinedCard {
            GifImage(
                imageID = R.drawable.num_sum_unlock_tutorial,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }

    if (!context.isPermissionGranted(Manifest.permission.CAMERA)) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CAMERA),
            1
        )
    }
}

@Preview
@Composable
private fun UnlockScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            UnlockScreen(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
        }
    }
}