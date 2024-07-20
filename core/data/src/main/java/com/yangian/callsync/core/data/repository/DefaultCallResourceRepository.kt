package com.yangian.callsync.core.data.repository

import com.yangian.callsync.core.data.model.asEntity
import com.yangian.callsync.core.database.dao.CallResourcesDao
import com.yangian.callsync.core.database.model.CallSyncCallEntity
import com.yangian.callsync.core.database.model.asExternalModel
import com.yangian.callsync.core.model.CallResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCallResourceRepository @Inject constructor(
    private val callResourcesDao: CallResourcesDao
) : CallResourceRepository {

    override fun getCalls(): Flow<List<CallResource>> {
        return callResourcesDao.getCalls()
            .map { it.map(CallSyncCallEntity::asExternalModel) }
    }

    override suspend fun addCalls(calls: List<CallResource>) {
        callResourcesDao.insertCalls(
            calls.map {
                it.asEntity(
                )
            }
        )
    }

    override suspend fun deleteCalls() {
        callResourcesDao.deleteCalls()
    }

    override suspend fun deleteCall(call: CallResource) {
        callResourcesDao.deleteCall(call.asEntity())
    }
}