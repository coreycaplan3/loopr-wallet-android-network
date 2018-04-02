package com.loopring.looprwalletnetwork.models.etherscan.address

import com.google.gson.annotations.SerializedName

/**
 * Created by arknw229 on 2/28/18.
 *
 * Etherscan API
 * Retrieve account balance
 *
 * {
 *   status
 *   message
 *   balance
 * }
 *
 * @author arknw229
 */
data class BalanceResponse(
    /**
     * Status of the transaction
     */
    val status: Int? = null,

    /**
     * Transaction message
     */
    val message: String? = null,

    /**
     * Balance
     */
    @SerializedName("result")
    val balance: String? = null

)
