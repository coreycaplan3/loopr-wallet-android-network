package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.math.BigInteger
import java.util.*

open class LooprOrder : RealmObject() {

    /**
     * Order protocol
     * Example output - 0x847983c3a34afa192cfee860698584c030f4c9db1
     */
    @SerializedName("protocol")
    var protocol : String? = null

    /**
     * Order owner
     * Example output - 0x847983c3a34afa192cfee860698584c030f4c9db1
     */
    @SerializedName("owner")
    var owner : String? = null

    /**
     * Token to sell
     * Example output - 0x2956356cd2a2bf3202f771f50d3d14a367b48070
     */
    @SerializedName("tokenS")
    var toSell : String? = null

    /**
     * Token to buy
     * Example output - 0xef68e7c694f40c8202821edf525de3782458639f
     */
    @SerializedName("tokenB")
    var toBuy : String? = null

    /**
     * Amount of [toSell] tokens to sell
     * Example output - "0x1a055690d9db80000"
     */
    var amtToSell: BigInteger?
        get() {
            return mAmtToSell?.let { BigInteger(it, 16) }
        }
        set(value) {
            mAmtToSell = value?.toString(16)
        }

    @SerializedName("amountS")
    private var mAmtToSell : String? = null

    /**
     * Amount of [toBuy] tokens to sell
     * Example output - "0x1a055690d9db80000"
     */
    var amtToBuy: BigInteger?
        get() {
            return mAmtToBuy?.let { BigInteger(it, 16) }
        }
        set(value) {
            mAmtToBuy = value?.toString(16)
        }

    @SerializedName("amountB")
    private var mAmtToBuy : String? = null

    /**
     * Indicating when this order is created
     * Example output - 1406014710 (returned as [Date] object)
     */
    @SerializedName("validSince")
    var validSince : Date? = null


    /**
     * How long, in seconds, will this order live
     * Example output - 1200 (meaning 20 minutes)
     */
    @SerializedName("validUntil")
    var mValidUntil : Integer? = null

    /**
     * Max amount of LRC to pay for miner. The real amount to pay is proportional to fill amount
     * Example output - "0x14"
     */
    var lrcFee: BigInteger?
        get() {
            return mLrcFee?.let { BigInteger(it, 16) }
        }
        set(value) {
            mLrcFee = value?.toString(16)
        }

    @SerializedName("lrcFee")
    private var mLrcFee : String? = null

    /**
     * If true, this order does not accept buying more than [buyAmt]
     * Example output - true
     */
    @SerializedName("buyNoMoreThanAmountB")
    var buyNoMoreThanBuyAmt : Boolean? = null

    /**
     * The percentage of savings paid to miner (0-100)
     * Example output - 50 (50% paid to miner)
     */
    @SerializedName("marginSplitPercentage")
    var marginSplitPercentage : Integer? = null

    /**
     * ECDSA signature parameter v
     */
    @SerializedName("v")
    var v : String? = null

    /**
     * ECDSA signature parameter r
     */
    @SerializedName("r")
    var r : String? = null

    /**
     * ECDSA signature parameter s
     */
    @SerializedName("s")
    var s : String? = null

}