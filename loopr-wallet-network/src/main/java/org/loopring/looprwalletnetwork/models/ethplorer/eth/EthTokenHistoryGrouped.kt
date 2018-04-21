package org.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.annotations.SerializedName
import org.loopring.looprwalletnetwork.models.ethplorer.transactioninfo.EthCountedTransaction
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * period:  show operations of specified days number only [optional, 30 days if not set, max. is 90 days]
 *
 * ```
 * {
 *   countTxs: [        # grouped token history
 *     {
 *       _id: {
 *         year:        # transaction year
 *         month:       # transaction month
 *         day:         # transaction day
 *       },
 *       ts:            # timestamp of the first transaction in group
 *       cnt:           # number of transaction in group
 *     },
 *     ...
 *   ]
 *   totals: {
 *     "tokens":        # total number of tokens listed
 *     "tokensWithPrice": # total number of tokens with price data provided
 *     "cap":           # total current market cap of tokens listed
 *     "capPrevious":   # total previous market cap of tokens listed
 *     "volume24h":     # cumulative 24 hour volume on the tokens listed
 *     "volumePrevious": # previous cumulative volume on the tokens listed
 *   }
 * }
 * ```
 *
 * @author arknw229
 */
open class EthTokenHistoryGrouped(

        /**
         * A list of [EthCountedTransaction] objects that give data on the number of transactions at given days
         */
        @SerializedName("countTxs")
        var countedTransactions: RealmList<EthCountedTransaction>? = null,

        /**
         * Cumulative transaction history data over the given time period
         */
        var totals: EthTokenHistoryTotals? = null
) : RealmObject()
