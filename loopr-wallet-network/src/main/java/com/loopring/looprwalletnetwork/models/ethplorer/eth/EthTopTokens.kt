package com.loopring.looprwalletnetwork.models.ethplorer.eth

import com.loopring.looprwalletnetwork.models.ethplorer.eth.EthTopTokens.Companion.SORT_CAPITALIZATION
import com.loopring.looprwalletnetwork.models.ethplorer.eth.EthTopTokens.Companion.SORT_OPERATION_COUNT
import com.loopring.looprwalletnetwork.models.ethplorer.eth.EthTopTokens.Companion.SORT_TRADE_VOLUME
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Information about the top tokens at the time of the request
 *
 * The sorting method for the top tokens are either [SORT_TRADE_VOLUME], [SORT_CAPITALIZATION], or
 * [SORT_OPERATION_COUNT].
 *
 * @author arknw229
 */
open class EthTopTokens(

        /**
         * List of token information in the form of [EthTokenInfo] objects
         */
        var tokens: RealmList<EthTokenInfo>? = null
) : RealmObject() {

    companion object {
        /**
         * Value to be used int he API request for sort by number of trades
         */
        const val SORT_TRADE_VOLUME = "trade"

        /**
         * Value to be used int he API request for sort by market cap
         */
        const val SORT_CAPITALIZATION = "cap"

        /**
         * Value to be used int he API request for sort by number of tokens
         */
        const val SORT_OPERATION_COUNT = "count"
    }
}
