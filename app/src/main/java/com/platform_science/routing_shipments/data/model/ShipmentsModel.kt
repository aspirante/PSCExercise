package com.platform_science.routing_shipments.data.model

import com.google.gson.annotations.SerializedName

/**
 * Shipments model
 */
data class ShipmentsModel(
    @SerializedName("drivers") val drivers: List<String>,
    @SerializedName("shipments") val shipments: List<String>
)
