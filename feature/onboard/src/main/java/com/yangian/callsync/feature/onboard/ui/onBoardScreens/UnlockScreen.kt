package com.yangian.callsync.feature.onboard.ui.onBoardScreens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.component.CalculatorButton
import com.yangian.callsync.core.designsystem.component.CallSyncAppBackground
import com.yangian.callsync.core.designsystem.component.GifImage
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTypography
import com.yangian.callsync.feature.onboard.R

@Composable
fun UnlockScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "1. In the NumSum press the keys in this order:",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(
            modifier = Modifier.fillMaxHeight(0.01f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            CalculatorButton(
                text = "=",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = "1",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = "8",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = "5",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = "6",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = "8",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(ratio = 1f, true),
            )

            CalculatorButton(
                text = "=",
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
}

@Preview
@Composable
private fun UnlockScreenPreview() {
    CallSyncAppTheme {
        CallSyncAppBackground {
            UnlockScreen(modifier = Modifier.padding(16.dp))
        }
    }
}