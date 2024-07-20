package com.yangian.callsync.core.firebase.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.text.Typography.dagger

@Module
@InstallIn(SingletonComponent::class)
internal object FirestoreModule {

    @Provides
    fun providesFirebaseFirestore(
        firebase: Firebase
    ): FirebaseFirestore {
        val firestore = firebase.firestore
//        firestore.useEmulator("10.0.0.2", 4000)
        return firestore
    }
}