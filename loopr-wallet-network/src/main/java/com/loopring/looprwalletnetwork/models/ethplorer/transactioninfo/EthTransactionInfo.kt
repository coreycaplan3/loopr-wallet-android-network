package com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Information on a transaction
 *
 * @author arknw229
 */
open class EthTransactionInfo(

        /**
         * Hash of the transaction
         */
        var hash: String? = null,

        /**
         * Timestamp from when the transaction occurred
         */
        var timestamp: Long? = null,

        /**
         * Block number of the block the transaction was mined onto
         */
        var blockNumber: Long? = null,

        /**
         * The number of confirmations on the block the transaction is on
         */
        var confirmations: Long? = null,

        /**
         * Success or failure of the transaction
         */
        var success: Boolean = false,

        /**
         * The address the transaction came from
         */
        var from: String? = null,

        /**
         * The address the transaction is to
         */
        var to: String? = null,

        /**
         * Transaction value, measured as Ether
         */
        var value: Long? = null,

        /**
         * The input code from the transaction. Only applicable for a contract
         */
        var input: String? = null,

        /**
         * The gas limit of the transaction, in GWEI
         */
        var gasLimit: Long? = null,

        /**
         * The gas used in the transaction, in GWEI
         */
        var gasUsed: Long? = null,

        /**
         * Log data associated with the transaction
         */
        var logs: RealmList<EthTransactionLogInfo>? = null,

        /**
         * Operations associated with the transaction
         */
        var operations: RealmList<EthTransactionOperationInfo>? = null
) : RealmObject()