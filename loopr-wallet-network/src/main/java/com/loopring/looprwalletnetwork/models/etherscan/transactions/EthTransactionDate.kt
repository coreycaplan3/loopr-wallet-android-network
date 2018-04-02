package com.loopring.looprwalletnetwork.models.etherscan.transactions

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * The year month and day of a transaction
 *
 * @author arknw229
 */
class EthTransactionDate (
    /**
     * Year the transaction occurred in
     */
    var year: Int? = null,

    /**
     * Month the transaction occurred in
     */
    var month: Int? = null,

    /**
     * Day the transaction occurred in
     */
    var day: Int? = null
)
