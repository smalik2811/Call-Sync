package com.yangian.callsync.core.data.di

import com.yangian.callsync.core.data.repository.CallResourceRepository
import com.yangian.callsync.core.data.repository.DefaultCallResourceRepository
import com.yangian.callsync.core.data.util.ConnectivityManagerNetworkMonitor
import com.yangian.callsync.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // Change to ActivityComponent
interface DataModule {

    @Binds
    fun bindsCallResourceRepository(
        callResourceRepository: DefaultCallResourceRepository,
    ): CallResourceRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}