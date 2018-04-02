package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import java.math.BigDecimal

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Cumulative market data related to a given token address
 *
 * {
 *     "tokens": # total number of tokens listed
 *     "tokensWithPrice": # total number of tokens with price data provided
 *     "cap": # total current market cap of tokens listed
 *     "capPrevious": # total previous market cap of tokens listed
 *     "volume24h": # cumulative 24 hour volume on the tokens listed
 *     "volumePrevious": # previous cumulative volume on the tokens listed
 * }
 *
 * @author arknw229
 */
class EthTokenHistoryTotals(
        /**
         * Total number of tokens listed
         */
        var tokens: Long? = null,

        /**
         * Total number of tokens with price data provided
         */
        var tokensWithPrice: Int? = null,

        /**
         * Total current market cap of tokens listed
         */
        var cap: BigDecimal? = null,

        /**
         * Total previous market cap of tokens listed
         */
        var capPrevious: BigDecimal? = null,

        /**
         * Cumulative 24 hour volume on the tokens listed
         */
        var volume24h: BigDecimal? = null,

        /**
         * Previous cumulative volume on the tokens listed
         */
        var volumePrevious: BigDecimal? = null
)
