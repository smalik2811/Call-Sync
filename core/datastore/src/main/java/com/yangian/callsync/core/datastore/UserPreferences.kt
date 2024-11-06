package com.yangian.callsync.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yangian.callsync.core.constant.Constant.HANDSHAKE_KEY
import com.yangian.callsync.core.constant.Constant.ONBOARDING_DONE_KEY
import com.yangian.callsync.core.constant.Constant.SENDER_ID
import com.yangian.callsync.core.constant.Constant.WORKER_RETRY_POLICY_KEY
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
        val Sender_Id_Key = stringPreferencesKey(SENDER_ID)
        val Onboarding_Done_Key = booleanPreferencesKey(ONBOARDING_DONE_KEY)
        val HandShake_Key = stringPreferencesKey(HANDSHAKE_KEY)
        val WorkerRetry_Policy_Key = longPreferencesKey(WORKER_RETRY_POLICY_KEY)
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    fun getSenderId(): Flow<String> {
        return dataStore.data.map {
            it[Sender_Id_Key] ?: ""
        }
    }

    suspend fun setSenderId(
        newSenderId: String
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[Sender_Id_Key] = newSenderId
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
            it[Onboarding_Done_Key] ?: false
        }
    }

    suspend fun setOnboardingDone(
        newOnboardingDone: Boolean
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[Onboarding_Done_Key] = newOnboardingDone
            }
        }
    }

    fun getWorkerRetryPolicy(): Flow<Long> {
        return dataStore.data.map {
            it[WorkerRetry_Policy_Key] ?: 6 // Default value of 6 hours
        }
    }

    suspend fun setWorkerRetryPolicy(
        newWorkerRetryPolicy: Long
    ) {
        withContext(Dispatchers.IO) {
            dataStore.edit {
                it[WorkerRetry_Policy_Key] = newWorkerRetryPolicy
            }
        }
    }

}
