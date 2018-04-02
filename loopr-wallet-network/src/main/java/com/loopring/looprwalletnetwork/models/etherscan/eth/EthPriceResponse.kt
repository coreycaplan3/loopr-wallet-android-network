package com.loopring.looprwalletnetwork.models.etherscan.eth

import com.google.gson.annotations.SerializedName

/**
 * Created by arknw229 on 3/1/18.
 *
 * Etherscan API
 * API response with Ethereum price data
 *
 * {
 *   status
 *   message
 *   result {
 *     ethbtc
 *     ethbtc_timestamp
 *     ethusd
 *     ethusd_timestamp
 *   }
 * }
 *
 * @author arknw229
 */
class EthPriceResponse (
    /**
     * Status of the response
     */
    var status: Int? = null,

    /**
     * Message describing the status of the response
     */
    var message: String? = null,

    /**
     * [EthPriceData] price data response
     */
    @SerializedName("result")
    var priceData: EthPriceData? = null
) {
}
