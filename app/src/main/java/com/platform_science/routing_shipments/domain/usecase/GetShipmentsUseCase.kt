package com.platform_science.routing_shipments.domain.usecase

import com.platform_science.routing_shipments.data.model.ShipmentsModel
import com.platform_science.routing_shipments.domain.repository.ShipmentsRepository
import javax.inject.Inject

/**
 * Use case class of shipments
 *
 * @constructor Inject shipments repository
 */
class GetShipmentsUseCase @Inject constructor(private val repository: ShipmentsRepository) {

    /**
     * Fetch all shipments data
     *
     * @return Shipments model
     */
    suspend operator fun invoke(): ShipmentsModel {
        return repository.getAllShipments()
    }

}