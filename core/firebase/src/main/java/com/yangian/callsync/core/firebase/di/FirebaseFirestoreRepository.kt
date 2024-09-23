package com.yangian.callsync.core.firebase.di

import com.google.firebase.firestore.FirebaseFirestore
import com.yangian.callsync.core.firebase.repository.DefaultFirestoreRepository
import com.yangian.callsync.core.firebase.repository.FirestoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseFirestoreRepository {
    @Provides
    fun providesFirebaseFirestoreRepository(
        firebaseFirestore: FirebaseFirestore
    ): FirestoreRepository = DefaultFirestoreRepository(
        firebaseFirestore
    )
}