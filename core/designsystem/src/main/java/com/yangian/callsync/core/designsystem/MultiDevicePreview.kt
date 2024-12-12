package com.yangian.callsync.core.designsystem

import androidx.compose.ui.tooling.preview.Preview

@Preview(device = "spec:parent=pixel_9_pro_xl", name = "Phone", group = "Portrait")
@Preview(name = "Tablet", device = "spec:parent=pixel_c,orientation=portrait", group = "Portrait")
@Preview(
    name = "Desktop",
    device = "spec:parent=desktop_large,orientation=portrait", group = "Portait"
)

@Preview(device = "spec:parent=pixel_9_pro_xl,orientation=landscape", name = "Phone", group = "Landscape")
@Preview(name = "Tablet", device = "spec:parent=pixel_c", group = "Landscape")
@Preview(
    name = "Desktop",
    device = "spec:parent=desktop_large", group = "Landscape"
)
annotation class MultiDevicePreview