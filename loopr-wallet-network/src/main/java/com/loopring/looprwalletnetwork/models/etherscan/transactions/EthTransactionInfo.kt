package com.example.arknw229.networkingtests

import com.loopring.looprwalletnetwork.models.etherscan.transactions.EthTransactionLogInfo
import com.loopring.looprwalletnetwork.models.etherscan.transactions.EthTransactionOperationInfo

/**
 * Created by arknw229 on 3/13/18.
 */

/**
 * Ethplorer API
 * Information on a transaction
 */
class EthTransactionInfo (
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
     * The number of confimrations on the block the transaction is on
     */
    var confirmations: Long? = null,

        /**
     * Success or failure of the trasaction
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
     * Transaction value
     */
    var value: Long? = null,

        /**
     * The input code from the transaction. Only applicable for a contract
     */
    var input: String? = null,

        /**
     * The gas limit of the transaction
     */
    var gasLimit: Long? = null,

        /**
     * The gas used in the transaction
     */
    var gasUsed: Long? = null,

        /**
     * Log data associated with the transaction
     */
    var logs: Array<EthTransactionLogInfo>? = null,

        /**
     * Operations associated with the transaction
     */
    var operations: Array<EthTransactionOperationInfo>? = null

)
