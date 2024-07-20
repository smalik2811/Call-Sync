package com.yangian.callsync.core.data.model

import com.yangian.callsync.core.database.model.CallSyncCallEntity
import com.yangian.callsync.core.model.CallResource

fun CallResource.asEntity() = CallSyncCallEntity(
    id = id,
    name = name,
    number = number,
    date = timestamp,
    duration = duration,
    type = type,
)