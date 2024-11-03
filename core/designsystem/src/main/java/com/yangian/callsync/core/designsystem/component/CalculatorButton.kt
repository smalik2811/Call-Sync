package com.yangian.callsync.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.yangian.callsync.core.designsystem.theme.CallSyncAppTheme

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Preview(
    showSystemUi = true, device = "id:pixel_8_pro",
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, apiLevel = 34
)
@Composable
private fun CalculatorButtonPreview() {
    CallSyncAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CalculatorButton(
                    text = "=",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
                CalculatorButton(
                    text = "1",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
                CalculatorButton(
                    text = "8",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
                CalculatorButton(
                    text = "5",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
                CalculatorButton(
                    text = "6",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
                CalculatorButton(
                    text = "8",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
                CalculatorButton(
                    text = "=",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ratio = 1f, true),
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}