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

    @Query("SELECT * FROM $CALL_TABLE_NAME ORDER BY id DESC")
    fun getCalls(): Flow<List<CallSyncCallEntity>>

//    @RawQuery(observedEntities = [CallSyncCallEntity::class])
//    fun getComplexCallsQuery(query: SupportSQLiteQuery):
//            Flow<List<CallSyncCallEntity>>
//
//    fun getCalls(): Flow<List<CallSyncCallEntity>> {
//        val query = """
//        SELECT t1.*
//        FROM (
//            SELECT *, ROW_NUMBER() OVER (PARTITION BY strftime('%Y-%m-%d', date / 1000, 'unixepoch')) AS row_num
//            FROM $CALL_TABLE_NAME
//        ) AS t1
//        WHERE row_num = 1
//        ORDER BY id DESC
//    """.trimIndent()
//
//        return getComplexCallsQuery(SimpleSQLiteQuery(query))
//    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalls(calls: List<CallSyncCallEntity>)

    @Query("DELETE FROM $CALL_TABLE_NAME")
    suspend fun deleteCalls()

    @Delete
    suspend fun deleteCall(call: CallSyncCallEntity)
}