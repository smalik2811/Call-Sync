package com.yangian.callsync.feature.onboard.model

import androidx.annotation.StringRes
import com.yangian.callsync.feature.onboard.R

enum class OnBoardingScreens(
    @StringRes
    val title: Int
) {
    Welcome(R.string.welcome_title),
    Install(R.string.install_title),
    Unlock(R.string.unlock_title),
    Connection1(R.string.connection_title),
    Connection2(R.string.connection_title),
    DkmaScreen(R.string.dkma_title)
}