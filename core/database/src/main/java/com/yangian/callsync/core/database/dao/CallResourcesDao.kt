package com.yangian.callsync.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yangian.callsync.core.constant.Constant.CALL_TABLE_NAME
import com.yangian.callsync.core.database.model.CallSyncCallEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CallResourcesDao {

    @Query("SELECT * FROM $CALL_TABLE_NAME ORDER BY rowid DESC")
    fun getCalls(): Flow<List<CallSyncCallEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalls(calls: List<CallSyncCallEntity>)

    @Query("DELETE FROM $CALL_TABLE_NAME")
    suspend fun deleteCalls()

    @Delete
    suspend fun deleteCall(call: CallSyncCallEntity)
}