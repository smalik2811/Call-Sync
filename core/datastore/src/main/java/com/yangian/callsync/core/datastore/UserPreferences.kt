package com.yangian.callsync.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yangian.callsync.core.constant.Constant.HANDSHAKE_KEY
import com.yangian.callsync.core.constant.Constant.ONBOARDING_DONE
import com.yangian.callsync.core.constant.Constant.SENDER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val SENDER_ID_KEY = stringPreferencesKey(SENDER_ID)
        val ONBOARDING_DONE_KEY = booleanPreferencesKey(ONBOARDING_DONE)
        val HandShake_Key = stringPreferencesKey(HANDSHAKE_KEY)
        val WorkerRetryPolicy_Key = longPreferencesKey("WORKER_RETRY_POLICY")
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    fun getSenderId(): Flow<String> {
        return dataStore.data.map {
            it[SENDER_ID_KEY] ?: ""
        }
    }

    suspend fun setSenderId(
        newSenderId: String
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[SENDER_ID_KEY] = newSenderId
            }
        }
    }

    fun getHandShakeKey(): Flow<String?> {
        return dataStore.data.map {
            it[HandShake_Key]
        }
    }

    suspend fun setHandShakeKey(
        newHandShakeKey: String
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[HandShake_Key] = newHandShakeKey
            }
        }
    }

    fun getOnboardingDone(): Flow<Boolean> {
        return dataStore.data.map {
            it[ONBOARDING_DONE_KEY] ?: false
        }
    }

    suspend fun setOnboardingDone(
        newOnboardingDone: Boolean
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[ONBOARDING_DONE_KEY] = newOnboardingDone
            }
        }
    }

    fun getWorkerRetryPolicy(): Flow<Long> {
        return dataStore.data.map {
            it[WorkerRetryPolicy_Key] ?: 6 // Default value of 6 hours
        }
    }

    suspend fun setWorkerRetryPolicy(
        newWorkerRetryPolicy: Long
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[WorkerRetryPolicy_Key] = newWorkerRetryPolicy
            }
        }
    }

}
