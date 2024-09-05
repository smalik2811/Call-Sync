package com.yangian.callsync.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DkmaManufacturer(
    val explanation: String,
    val user_solution: String,
)
