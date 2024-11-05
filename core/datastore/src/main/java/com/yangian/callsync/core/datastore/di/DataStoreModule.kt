package com.yangian.callsync.core.datastore.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.yangian.callsync.core.constant.Constant.CALLSYNC_PREFERENCES_DATASTORE
import com.yangian.callsync.core.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferences(
        @ApplicationContext context: Context,
    ): UserPreferences {

        val masterKeyAlias = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val dataStore = EncryptedSharedPreferences
            .create(
                context,
                CALLSYNC_PREFERENCES_DATASTORE,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ) as EncryptedSharedPreferences

        return UserPreferences(
            dataStore
        )
    }
}