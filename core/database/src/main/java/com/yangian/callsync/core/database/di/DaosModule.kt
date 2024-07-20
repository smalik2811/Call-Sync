package com.yangian.callsync.core.database.di

import com.yangian.callsync.core.database.CallSyncCallDatabase
import com.yangian.callsync.core.database.dao.CallResourcesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesCallResourcesDao(
        callSyncCallDatabase: CallSyncCallDatabase
    ): CallResourcesDao = callSyncCallDatabase.callResourceDao()
}