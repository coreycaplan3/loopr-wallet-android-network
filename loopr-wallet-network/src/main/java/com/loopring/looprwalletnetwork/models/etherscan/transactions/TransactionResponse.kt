package com.loopring.looprwalletnetwork.models.etherscan.transactions

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/1/18.
 *
 * Etherscan API
 *
 * Data about a transaction on the blockchain
 *
 * @author arknw229
 */
open class TransactionResponse(

        /**
         * Status of the request. Can be 1 for complete or 0 for failure.
         */
        var status: Int? = null,

        /**
         * Status message. Can be "OK" for successful calls or "NOTOK" for failures
         */
        var message: String? = null,

        /**
         * List of results of the transaction in a [transactionList] object
         */
        @SerializedName("result")
        var transactionList: RealmList<IndividualTransaction>? = null

) : RealmObject()