package org.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.math.BigDecimal
import java.util.*

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Current price and volume data on a token
 *
 * @author arknw229
 */
open class EthTokenCurrentInfo : RealmObject() {

    /**
     * The price that the token is going for
     */
    var rate: BigDecimal?
        get() {
            return mRate?.let { BigDecimal(it) }
        }
        set(value) {
            mRate = value?.toPlainString()
        }

    @SerializedName("rate")
    private var mRate: String? = null

    /**
     * Percentage of how the price has changed
     */
    var diff: BigDecimal?
        get() {
            return mDiff?.let { BigDecimal(it) }
        }
        set(value) {
            mDiff = value?.toPlainString()
        }

    @SerializedName("diff")
    private var mDiff: String? = null

    /**
     * Percentage of how the price has changed over the last 7 days
     */
    var diff7d: BigDecimal?
        get() {
            return mDiff7d?.let { BigDecimal(it) }
        }
        set(value) {
            mDiff7d = value?.toPlainString()
        }

    @SerializedName("diff7d")
    private var mDiff7d: String? = null

    /**
     * Market capitalization of the token in USD
     */
    var marketCapUsd: BigDecimal?
        get() {
            return mMarketCapUsd?.let { BigDecimal(it) }
        }
        set(value) {
            mMarketCapUsd = value?.toPlainString()
        }

    @SerializedName("marketCapUsd")
    private var mMarketCapUsd: String? = null

    /**
     * Available supply of the token. The scale is set so 100,000,000 is written as *100000000.0*.
     */
    var availableSupply: BigDecimal?
        get() {
            return mAvailableSupply?.let { BigDecimal(it) }
        }
        set(value) {
            mAvailableSupply = value?.toPlainString()
        }

    @SerializedName("availableSupply")
    private var mAvailableSupply: String? = null

    /**
     * 24 hour volume of the token
     */
    var volume24h: BigDecimal?
        get() {
            return mVolume24h?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume24h = value?.toPlainString()
        }

    @SerializedName("volume24h")
    private var mVolume24h: String? = null

    /**
     * Timestamp of when the market data was taken from
     */
    @SerializedName("ts")
    var timestamp: Date? = null
}
