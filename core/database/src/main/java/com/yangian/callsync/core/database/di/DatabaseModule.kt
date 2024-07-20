package com.yangian.callsync.core.database.di

import android.content.Context
import androidx.room.Room
import com.yangian.callsync.core.constant.Constant.CALL_DATABASE_NAME
import com.yangian.callsync.core.database.CallSyncCallDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesCallDatabase(
        @ApplicationContext context: Context,
    ) : CallSyncCallDatabase = Room.databaseBuilder(
        context = context,
        klass = CallSyncCallDatabase::class.java,
        name = CALL_DATABASE_NAME
    ).build()
}