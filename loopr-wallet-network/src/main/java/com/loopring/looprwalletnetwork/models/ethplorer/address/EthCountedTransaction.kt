package com.loopring.looprwalletnetwork.models.ethplorer.address

import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.models.etherscan.transactions.EthTransactionDate

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * A count of the number of transactions at a given date
 *
 * {
 *   _id: {
 *     year:  # transaction year
 *     month: # transaction month
 *     day:   # transaction day
 *   },
 *   ts:        # timestamp of the first transaction in group
 *   cnt:       # number of transaction in group
 * }
 *
 * @author arknw229
 */
class EthCountedTransaction (
        /**
     * Object containing the year, month, and day
     */
    @SerializedName("_id")
    var transactionDate: EthTransactionDate? = null,

        /**
     * Timestamp of the transaction
     */
    @SerializedName("ts")
    var timestamp: Long? = null,

        /**
     * Count of the transactions
     */
    @SerializedName("cnt")
    var count: Long? = null
)
