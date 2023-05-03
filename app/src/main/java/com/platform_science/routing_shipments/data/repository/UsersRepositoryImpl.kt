package com.platform_science.routing_shipments.data.repository

import com.platform_science.routing_shipments.PSCodeApplication.Companion.utils
import com.platform_science.routing_shipments.common.Constants
import com.platform_science.routing_shipments.data.model.ShipmentsModel
import com.platform_science.routing_shipments.domain.repository.ShipmentsRepository
import javax.inject.Inject

/**
 * Implementation of repository to fetching data
 */
class UsersRepositoryImpl @Inject constructor() : ShipmentsRepository {

    /**
     * Fetch all data from the file to parse it and casting to model
     * @return Parsing json data to shipments model
     */
    override suspend fun getAllShipments(): ShipmentsModel {
        return utils!!.getParserShipments(utils!!.getDataFromJsonFile(Constants.SHIPMENTS_FILE_NAME))
    }

}