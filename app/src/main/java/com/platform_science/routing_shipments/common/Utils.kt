package com.platform_science.routing_shipments.common

import android.content.Context
import com.google.gson.Gson
import com.platform_science.routing_shipments.data.model.ShipmentsModel

/**
 * This class is used as an extension
 *
 * @property context receive application context
 */
class Utils(private val context: Context) {

    /**
     * Read file from assets
     *
     * @param fileName file name to read
     * @return Content string
     */
    fun getDataFromJsonFile(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    /**
     * Change content string to model
     *
     * @param response Content string
     * @return Model<ShipmentsModel>
     */
    fun getParserShipments(response: String): ShipmentsModel {
        return Gson().fromJson(response, ShipmentsModel::class.java)
    }

    /**
     * Generate random numbers
     *
     * @param start Minimal range
     * @param end Maximum range
     */
    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }

}