package com.yangian.callsync.core.data.repository

import com.yangian.callsync.core.model.CallResource
import kotlinx.coroutines.flow.Flow

interface CallResourceRepository {

    fun getCalls(): Flow<List<CallResource>>

    suspend fun addCalls(calls: List<CallResource>)

    suspend fun deleteCalls()

    suspend fun deleteCall(call: CallResource)

}