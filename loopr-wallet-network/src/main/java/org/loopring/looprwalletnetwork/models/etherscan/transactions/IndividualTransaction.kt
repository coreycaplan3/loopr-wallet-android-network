package org.loopring.looprwalletnetwork.models.etherscan.transactions

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

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
        var timeStamp: String? = null,

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
         * Gas used for the transaction
         */
        var gas: String? = null,

        /**
         * Gas price used for the transaction
         */
        var gasPrice: String? = null,

        /**
         * Check if there's an error
         */
        var isError: String? = null,

        /**
         * Status on the receipt of the transaction
         */
        var txReceiptStatus: String? = null,

        /**
         * Input for the transaction (if the transaction interacted with a smart contract)
         */
        var input: String? = null,

        /**
         * Cumulative gas used
         */
        var cumulativeGasUsed: String? = null,

        /**
         * Gas used for the transaction
         */
        var gasUsed: String? = null,

        /**
         * Number of confirmations the transaction has accumulated
         */
        var confirmations: String? = null

) : RealmObject()