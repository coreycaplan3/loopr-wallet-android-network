package com.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo.EthCountedTransaction
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Get grouped historical price data
 *
 * @author arknw229
 */
open class EthTokenPriceHistoryGroupedInfo(

        /**
         * Information on the current price data related to the token
         */
        var current: EthTokenCurrentInfo? = null,

        /**
         * A list of [EthCountedTransaction] objects that count the number of transactions performed on each day
         */
        @SerializedName("countTxs")
        var countedTransactions: RealmList<EthCountedTransaction>? = null,

        /**
         * A list of [EthTokenPriceInfo] objects that correspond to the list in [countedTransactions] and store price data
         */
        var prices: RealmList<EthTokenPriceInfo>? = null
) : RealmObject()