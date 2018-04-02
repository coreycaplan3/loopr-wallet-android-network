package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import com.loopring.looprwalletnetwork.models.ethplorer.tokens.EthTopTokens.Companion.CAPITALIZATION_SORT
import com.loopring.looprwalletnetwork.models.ethplorer.tokens.EthTopTokens.Companion.OPERATION_COUNT_SORT
import com.loopring.looprwalletnetwork.models.ethplorer.tokens.EthTopTokens.Companion.TRADE_VOLUME_SORT

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Information about the top tokens at the time of the request
 * The sorting method for the top tokens are either [TRADE_VOLUME_SORT], [CAPITALIZATION_SORT], or
 * [OPERATION_COUNT_SORT].
 *
 * @author arknw229
 */
class EthTopTokens {

    /**
     * List of token information in the form of [EthTokenInfo] objects
     */
    var tokens: MutableList<EthTokenInfo>? = null

    companion object {
        /**
         * Value to be used int he API request for sort by number of trades
         */
        val TRADE_VOLUME_SORT = "trade"

        /**
         * Value to be used int he API request for sort by market cap
         */
        val CAPITALIZATION_SORT = "cap"

        /**
         * Value to be used int he API request for sort by number of tokens
         */
        val OPERATION_COUNT_SORT = "count"
    }
}
