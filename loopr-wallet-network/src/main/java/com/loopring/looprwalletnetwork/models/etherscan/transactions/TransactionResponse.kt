package com.loopring.looprwalletnetwork.models.etherscan.transactions

import com.google.gson.annotations.SerializedName

/**
 * Created by arknw229 on 3/1/18.
 *
 * Etherscan API
 * Data about a transaction on the blockchain
 *
 * @author arknw229
 */
class TransactionResponse(
        /**
         * Status of the transaction
         */
        var status: Int? = null,

        /**
         * Message on the status of the transaction
         */
        var message: String? = null,

        /**
         * List of results of the transaction
         */
        @SerializedName("result")
        var transactions: List<IndividualTransaction>? = null
)
