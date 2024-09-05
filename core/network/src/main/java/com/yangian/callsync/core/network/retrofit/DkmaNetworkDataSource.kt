package com.yangian.callsync.core.network.retrofit

import com.yangian.callsync.core.network.model.DkmaManufacturer

interface DkmaNetworkDataSource {
    suspend fun getDkmaManufacturer(manufacturer: String): DkmaManufacturer
}