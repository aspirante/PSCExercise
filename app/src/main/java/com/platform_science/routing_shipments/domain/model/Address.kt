package com.platform_science.routing_shipments.domain.model

import com.platform_science.routing_shipments.common.Status

/**
 * data class of address
 */
data class Address(
    val address: String = "",
    var status: Int = Status.AVAILABLE.ordinal
    )
