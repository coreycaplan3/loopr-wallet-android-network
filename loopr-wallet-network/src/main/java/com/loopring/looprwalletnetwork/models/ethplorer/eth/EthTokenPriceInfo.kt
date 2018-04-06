package com.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Information about an ERC20 token at a given time
 *
 * @author arknw229
 */
open class EthTokenPriceInfo : RealmObject() {

    /**
     * UNIX timestamp of when the price was last updated
     */
    @SerializedName("ts")
    var timestamp: Long? = null

    /**
     * String representation of the timestamp, in YYYY-MM-DD format
     */
    @SerializedName("date")
    var dateString: String? = null

    /**
     * Hour. Usually 0.
     */
    var hour: Long = 0

    /**
     * What it opened the day at in USD. Example output - 0.147354
     */
    var open: BigDecimal?
        get() {
            return mOpen?.let { BigDecimal(it) }
        }
        set(value) {
            mOpen = value?.toPlainString()
        }

    @SerializedName("open")
    var mOpen: String? = null

    /**
     * What it closed the day at in USD. Example output - 0.147354
     */
    var close: BigDecimal?
        get() {
            return mClose?.let { BigDecimal(it) }
        }
        set(value) {
            mClose = value?.toPlainString()
        }

    @SerializedName("close")
    var mClose: String? = null

    /**
     * The high for the day in USD. Example output - 0.150938
     */
    var high: BigDecimal?
        get() {
            return mHigh?.let { BigDecimal(it) }
        }
        set(value) {
            mHigh = value?.toPlainString()
        }

    @SerializedName("high")
    var mHigh: String? = null

    /**
     * The lowest value for the day in USD. Example output - 0.143754
     */
    var low: BigDecimal?
        get() {
            return mLow?.let { BigDecimal(it) }
        }
        set(value) {
            mLow = value?.toPlainString()
        }

    @SerializedName("low")
    var mLow: String? = null

    /**
     * The number of tokens traded over the day. Example output - 1743199.9150653
     */
    var volume: BigDecimal?
        get() {
            return mVolume?.let { BigDecimal(it) }
        }
        set(value) {
            mVolume = value?.toPlainString()
        }

    @SerializedName("volume")
    var mVolume: String? = null

    /**
     * Volume in USD, $200.50 of volume is formatted as 200.50. Maximum 7 decimal places
     */
    var volumeConverted: BigDecimal?
        get() {
            return mVolumeConverted?.let { BigDecimal(it) }
        }
        set(value) {
            mVolumeConverted = value?.toPlainString()
        }

    @SerializedName("volumeConverted")
    var mVolumeConverted: String? = null

    /**
     * The average privce in USD for the day. Example output - 0.162039 would mean it was trading for on average ~16.2 cents
     */
    var average: BigDecimal?
        get() {
            return mAverage?.let { BigDecimal(it) }
        }
        set(value) {
            mAverage = value?.toPlainString()
        }

    @SerializedName("average")
    var mAverage: String? = null

    /**
     * The market cap for the day. Example output - 44855100
     */
    var cap: BigDecimal?
        get() {
            return mCap?.let { BigDecimal(it) }
        }
        set(value) {
            mCap = value?.toPlainString()
        }

    @SerializedName("cap")
    var mCap: String? = null

}
