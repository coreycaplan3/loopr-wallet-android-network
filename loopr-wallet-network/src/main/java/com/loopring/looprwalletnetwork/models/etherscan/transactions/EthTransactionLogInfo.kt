package com.loopring.looprwalletnetwork.models.etherscan.transactions

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Logged information about a transaction
 *
 * @author arknw229
 */
class EthTransactionLogInfo (
    /**
     * Logged addresses
     */
    var address: String? = null,

    /**
     * Logged topics
     */
    var topics: Array<String>? = null,

    /**
     * Logged data
     */
    var data: String? = null
)
