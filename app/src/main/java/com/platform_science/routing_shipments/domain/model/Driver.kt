package com.platform_science.routing_shipments.domain.model

import com.platform_science.routing_shipments.common.Status

/**
 * data class of driver
 */
data class Driver(
    val driver: String = "",
    var address: String = "",
    var status: Int = Status.AVAILABLE.ordinal
    )
