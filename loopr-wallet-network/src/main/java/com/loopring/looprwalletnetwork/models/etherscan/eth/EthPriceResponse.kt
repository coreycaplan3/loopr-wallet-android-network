package com.loopring.looprwalletnetwork.models.etherscan.eth

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/1/18.
 *
 * Etherscan API
 *
 * API response with Ethereum price data:
 *
 * ```
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
 * ```
 *
 * @author arknw229
 */
open class EthPriceResponse(

        /**
         * Status of the request. Can be 1 for complete or 0 for failure.
         */
        var status: Int? = null,

        /**
         * Status message. Can be "OK" for successful calls or "NOTOK" for failures
         */
        var message: String? = null,

        /**
         * [EthPriceData] price data response
         */
        @SerializedName("result")
        var priceData: EthPriceData? = null
) : RealmObject()