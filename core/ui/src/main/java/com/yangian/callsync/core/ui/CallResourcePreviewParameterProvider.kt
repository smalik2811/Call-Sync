package com.yangian.callsync.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.yangian.callsync.core.model.CallResource

class CallResourcePreviewParameterProvider : PreviewParameterProvider<List<CallResource>> {
    override val values: Sequence<List<CallResource>> = sequenceOf(
        listOf(
            CallResource(
                id = 1,
                name = "Steve Rogers",
                number = "+1 234 567 890",
                timestamp = (1546300800000L..1717027200000L).random(),
                duration = (0L..860L).random(),
                type = (1..7).random()
            ),
            CallResource(
                id = 2,
                name = "Bruce Banner",
                number = "+1 987 654 321",
                timestamp = (1546300800000L..1717027200000L).random(),
                duration = (0L..860L).random(),
                type = (1..7).random()
            ),
            CallResource(
                id = 3,
                name = "Hank Pym",
                number = "+1 000 454 803",
                timestamp = (1546300800000L..1717027200000L).random(),
                duration = (0L..860L).random(),
                type = (1..7).random()
            ),
            CallResource(
                id = 4,
                name = "Clint Barton",
                number = "+1 596 451 103",
                timestamp = (1546300800000L..1717027200000L).random(),
                duration = (0L..860L).random(),
                type = (1..7).random()
            ),
            CallResource(
                id = 5,
                name = "Tony Stark",
                number = "+1 455 515 666",
                timestamp = (1546300800000L..1717027200000L).random(),
                duration = (0L..860L).random(),
                type = (1..7).random()
            ),
            CallResource(
                id = 6,
                name = "Thor",
                number = "+1 565 415 897",
                timestamp = (1546300800000L..1717027200000L).random(),
                duration = (0L..860L).random(),
                type = (1..7).random()
            ),
        )
    )
}