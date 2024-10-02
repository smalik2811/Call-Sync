package com.yangian.callsync.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val csDispatchers: CSDispatchers)

enum class CSDispatchers {
    Default,
    IO,
}