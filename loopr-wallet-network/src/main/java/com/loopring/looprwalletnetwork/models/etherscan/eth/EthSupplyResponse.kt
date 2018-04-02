package com.loopring.looprwalletnetwork.models.etherscan.eth

import com.google.gson.annotations.SerializedName

/**
 * Created by arknw229 on 3/1/18.
 *
 * Etherscan API
 *
 * Retrieves the supply of eth
 *
 * @author arknw229
 */
class EthSupplyResponse (
    /**
     * The status of the request
     */
    var status: Int? = null,

    /**
     * The message of the status of the request
     */
    var message: String? = null,

    /**
     * The supply
     */
    @SerializedName("result")
    var supply: String? = null
)
