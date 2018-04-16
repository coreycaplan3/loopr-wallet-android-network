package org.loopring.looprwalletnetwork.models.etherscan.eth

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/1/18.
 *
 * Ethereum price data at a given time
 *
 * ```
 * {
 *   ethbtc
 *   ethbtc_timestamp
 *   ethusd
 *   ethusd_timestamp
 * }
 * ```
 *
 * @author arknw229
 */
open class EthPriceData : RealmObject() {

    /**
     * ETH/BTC ratio
     */
    var ethToBtc: BigDecimal?
        get() = mEthToBtc?.let { BigDecimal(it) }
        set(value) {
            mEthToBtc = value?.toPlainString()
        }

    @SerializedName("ethbtc")
    private var mEthToBtc: String? = null

    /**
     * Timestamp the ETH/BTC ratio was taken from
     */
    @SerializedName("ethbtc_timestamp")
    var ethToBtcTimestamp: String? = null

    /**
     * ETH/USD ratio
     */
    var ethToUsd: BigDecimal?
        get() = mEthToUsd?.let { BigDecimal(it) }
        set(value) {
            mEthToUsd = value?.toPlainString()
        }

    @SerializedName("ethusd")
    var mEthToUsd: String? = null

    /**
     * Timestamp the ETH/USD ratio was taken from
     */
    @SerializedName("ethusd_timestamp")
    var ethToUsdTimestamp: String? = null

}