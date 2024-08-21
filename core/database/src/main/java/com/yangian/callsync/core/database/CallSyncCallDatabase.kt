package com.yangian.callsync.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yangian.callsync.core.database.dao.CallResourcesDao
import com.yangian.callsync.core.database.model.CallSyncCallEntity

@Database(
    entities = [
        CallSyncCallEntity::class,
    ],
    version = 1,
    autoMigrations = [
    ],
    exportSchema = true,
)
internal abstract class CallSyncCallDatabase : RoomDatabase() {
    abstract fun callResourceDao(): CallResourcesDao
}