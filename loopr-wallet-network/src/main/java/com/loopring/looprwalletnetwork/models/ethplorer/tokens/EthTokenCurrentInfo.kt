package com.loopring.looprwalletnetwork.models.ethplorer.tokens

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Current price and volume data on a token
 *
 * @author arknw229
 */
class EthTokenCurrentInfo(
        /**
         * The price that the token is going for
         */
        var rate: BigDecimal? = null,

        /**
         * Percentage of how the price has changed
         */
        var diff: BigDecimal? = null,

        /**
         * Percentage of how the price has changed over the last 7 days
         */
        var diff7d: BigDecimal? = null,

        /**
         * Timestamp of when the market data was taken from
         */
        @SerializedName("ts")
        var timestamp: Long? = null,

        /**
         * Market capitalization of the token in USD
         */
        var marketCapUsd: BigDecimal? = null,

        /**
         * Available supply of the token
         */
        var availableSupply: BigDecimal? = null,

        /**
         * 24 hour volume of the token
         */
        var volume24h: BigDecimal? = null
)
