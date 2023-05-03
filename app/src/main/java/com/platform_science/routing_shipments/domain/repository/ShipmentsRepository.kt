package com.platform_science.routing_shipments.domain.repository

import com.platform_science.routing_shipments.data.model.ShipmentsModel

/**
 * Functions to fetch data
 */
interface ShipmentsRepository {

    suspend fun getAllShipments(): ShipmentsModel

}