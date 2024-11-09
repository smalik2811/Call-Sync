package com.yangian.callsync.core.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseAuthModule {

    @Provides
    fun providesFirebaseAuthentication(
        firebase: Firebase
    ): FirebaseAuth {
        val auth = firebase.auth
//        auth.useEmulator(" 192.168.1.36", 9099)
        return auth
    }
}