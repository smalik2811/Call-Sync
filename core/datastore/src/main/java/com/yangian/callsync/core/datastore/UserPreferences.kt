package com.yangian.callsync.core.datastore

import androidx.security.crypto.EncryptedSharedPreferences
import com.yangian.callsync.core.constant.Constant.HANDSHAKE_KEY
import com.yangian.callsync.core.constant.Constant.ONBOARDING_DONE_KEY
import com.yangian.callsync.core.constant.Constant.SENDER_ID_KEY
import com.yangian.callsync.core.constant.Constant.WORKER_RETRY_POLICY_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: EncryptedSharedPreferences
) {
    fun clear() {
        dataStore.edit().clear().apply()
    }

    fun getSenderId(): String =
        dataStore.getString(SENDER_ID_KEY, "")!!

    fun setSenderId(
        newSenderId: String
    ) {
        dataStore.edit().putString(SENDER_ID_KEY, newSenderId).apply()
    }

    fun getHandShakeKey(): String? = dataStore.getString(HANDSHAKE_KEY, null)

    fun setHandShakeKey(
        newHandShakeKey: String
    ) {
        dataStore.edit().putString(HANDSHAKE_KEY, newHandShakeKey).apply()
    }

    fun getOnboardingDone(): Boolean = dataStore.getBoolean(ONBOARDING_DONE_KEY, false)

    fun setOnboardingDone(
        newOnboardingDone: Boolean
    ) {
        dataStore.edit().putBoolean(ONBOARDING_DONE_KEY, newOnboardingDone).apply()
    }

    fun getWorkerRetryPolicy(): Long = dataStore.getLong(WORKER_RETRY_POLICY_KEY, 6)

    fun setWorkerRetryPolicy(
        newWorkerRetryPolicy: Long
    ) {
        dataStore.edit().putLong(WORKER_RETRY_POLICY_KEY, newWorkerRetryPolicy).apply()
    }
}
