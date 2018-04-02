package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.models.ethplorer.address.EthCountedTransaction

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Get grouped historical price data
 *
 * @author arknw229
 */
class EthTokenPriceHistoryGroupedInfo (
        /**
     * Information on the current price data related to the token
     */
    var current: EthTokenCurrentInfo? = null,

        /**
     * A list of [EthCountedTransaction] objects that count the number of transactions performed on each day
     */
    @SerializedName("countTxs")
    var countedTransactions: Array<EthCountedTransaction>? = null,

        /**
     * A list of [EthTokenPriceInfo] objects that correspond to the list in [countedTransactions] and store price data
     */
    var prices: Array<EthTokenPriceInfo>? = null
)
