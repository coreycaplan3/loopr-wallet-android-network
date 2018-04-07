package com.loopring.looprwalletnetwork.models.etherscan.transactions

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.util.*

/**
 * Created by arknw229 on 3/16/18.
 *
 * Etherscan API
 *
 * Represents a single transaction on the Ethereum chain
 *
 * @author arknw229
 */
open class IndividualTransaction(

        /**
         * Block number the transaction is on
         */
        @SerializedName("blockNumber")
        var blockNumber: String? = null,

        /**
         * Timestamp the transaction occurred at
         */
        var timeStamp: Date? = null,

        /**
         * Hash of the transaction
         */
        var hash: String? = null,

        /**
         * Nonce of the transaction
         */
        var nonce: String? = null,

        /**
         * Hash of the block
         */
        var blockHash: String? = null,

        /**
         *
         */
        var transactionIndex: String? = null,

        /**
         * Address the transaction is from
         */
        var from: String? = null,

        /**
         * Address the transaction is to
         */
        var to: String? = null,

        /**
         * The value of the transaction, in Ether
         */
        var value: String? = null,

        /**
         * Gas used for the transaction in Gwei
         */
        var gas: String? = null,

        /**
         * Gas price used for the transaction in Gwei
         */
        var gasPrice: String? = null,

        /**
         * Check if there's an error. 0 for no error, 1 for an error
         */
        var isError: String? = null,

        /**
         * Status on the receipt of the transaction
         */
        @SerializedName("txreceipt_status")
        var txReceiptStatus: String? = null,

        /**
         * Input for the transaction (if the transaction interacted with a smart contract)
         */
        var input: String? = null,

        /**
         * Cumulative gas used in Gwei
         */
        var cumulativeGasUsed: String? = null,

        /**
         * Gas used for the transaction in Gwei
         */
        var gasUsed: String? = null,

        /**
         * Number of confirmations the transaction has accumulated
         */
        var confirmations: String? = null

) : RealmObject()