package com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * A count of the number of transactions at a given date
 *
 * ```
 * {
 *   _id: {
 *     year:  # transaction year
 *     month: # transaction month
 *     day:   # transaction day
 *   },
 *   ts:        # timestamp of the first transaction in group
 *   cnt:       # number of transaction in group
 * }
 * ```
 *
 * @author arknw229
 */
open class EthCountedTransaction(
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
) : RealmObject()
