package com.loopring.looprwalletnetwork.models.etherscan.eth

/**
 * Created by arknw229 on 3/1/18.
 *
 * Ethereum price data at a given time
 *
 * {
 *   ethbtc
 *   ethbtc_timestamp
 *   ethusd
 *   ethusd_timestamp
 * }
 *
 * @author arknw229
 */
class EthPriceData(
        /**
         * ETH/BTC ratio
         */
        var ethbtc: String? = null,

        /**
         * Timestamp the ETH/BTC ratio was taken from
         */
        var ethbtcTimestamp: String? = null,

        /**
         * ETH/USD ratio
         */
        var ethusd: String? = null,

        /**
         * Timestamp the ETH/USD ratio was taken from
         */
        var ethusdTimestamp: String? = null
)
