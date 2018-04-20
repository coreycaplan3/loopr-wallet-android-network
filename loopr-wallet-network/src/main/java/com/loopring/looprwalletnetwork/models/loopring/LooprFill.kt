package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger
import java.util.*

open class LooprFill : RealmObject() {

    /**
     * The loopring contract address
     * Example output - "0x4c44d51CF0d35172fCe9d69e2beAC728de980E9D"
     */
    @SerializedName("protocol")
    var protocol : String? = null

    /**
     * The order owner address
     * Example output - "0x66727f5DE8Fbd651Dc375BB926B16545DeD71EC9"
     */
    @SerializedName("owner")
    var owner : String? = null

    /**
     * The index of the ring
     * Example output - 100
     */
    @SerializedName("ringIndex")
    var ringIndex : Integer? = null

    /**
     * The timestamp of matching time
     * Example output - 1512631182
     */
    var createTime: Date?
        get() {
            return mCreateTime?.let { Date(it) }
        }
        set(value) {
            mCreateTime = value?.time
        }

    private var mCreateTime : Long? = null

    /**
     * The hash of the matching ring
     * Example output - "0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5"
     */
    @SerializedName("ringHash")
    var ringHash : String? = null

    /**
     * The transaction hash
     * Example output - "0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5"
     */
    @SerializedName("txHash")
    var txHash : String? = null

    /**
     * The order hash
     * Example output - "0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5"
     */
    @SerializedName("orderHash")
    var orderHash : String? = null

    /**
     * The matched sell amount
     * Example output - "0xde0b6b3a7640000"
     */
    var amountS : BigInteger?
        get() {
            return mAmountS?.let {BigInteger(it,16)}
        }
        set(value) {
            mAmountS = value?.toString(16)
        }

    private var mAmountS : String? = null

    /**
     * The matched buy amount
     * Example output - "0xde0b6b3a7640000"
     */
    var amountB : BigInteger?
        get() {
            return mAmountB?.let {BigInteger(it,16)}
        }
        set(value) {
            mAmountB = value?.toString(16)
        }

    private var mAmountB : String? = null

    /**
     * The matched sell token
     * Example output - "WETH"
     */
    @SerializedName("tokenS")
    var tokenS : String? = null

    /**
     * The matched buy token
     * Example output - "COSS"
     */
    @SerializedName("tokenB")
    var tokenB : String? = null

    /**
     * The amount of LRC paid by miner to order owner in exchange for margin split
     * Example output - "0xde0b6b3a7640000"
     */
    var lrcReward : BigInteger?
        get() {
            return mLrcReward?.let {BigInteger(it,16)}
        }
        set(value) {
            mLrcReward = value?.toString(16)
        }

    private var mLrcReward : String? = null

    /**
     * The real amount of LRC to pay for miner
     * Example output - "0xde0b6b3a7640000"
     */
    var lrcFee : BigInteger?
        get() {
            return mLrcFee?.let {BigInteger(it,16)}
        }
        set(value) {
            mLrcFee = value?.toString(16)
        }

    private var mLrcFee : String? = null

    /**
     * The tokenS paid to miner
     * Example output - "0xde0b6b3a7640000"
     */
    var splitS : BigInteger?
        get() {
            return mSplitS?.let {BigInteger(it,16)}
        }
        set(value) {
            mSplitS = value?.toString(16)
        }

    private var mSplitS : String? = null

    /**
     * The tokenB paid to miner
     * Example output - "0x0"
     */
    var splitB : BigInteger?
        get() {
            return mSplitB?.let {BigInteger(it,16)}
        }
        set(value) {
            mSplitB = value?.toString(16)
        }

    private var mSplitB : String? = null

    /**
     * The market being traded on
     * Example output - "LRC-WETH"
     */
    @SerializedName("market")
    var market : String? = null


    /**
     * Custom class deserializer
     */
    class LooprFillDeserializer : JsonDeserializer<LooprFill> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprFill? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val fill = LooprFill()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("protocol")?.let {
                    fill.protocol = it.asString
                }
                //}


                jsonObj.get("owner")?.let {
                    fill.owner  = it.asString
                }

                jsonObj.get("ringIndex")?.let {
                    fill.ringIndex = Integer(it.asString)
                }

                jsonObj.get("createTime")?.let {
                    fill.createTime = Date(it.asLong)
                }

                jsonObj.get("ringHash")?.let {
                    fill.ringHash = it.asString
                }

                jsonObj.get("txHash")?.let {
                    fill.txHash = it.asString
                }

                jsonObj.get("orderHash")?.let {
                    fill.orderHash = it.asString
                }

                jsonObj.get("amountS")?.let {
                    fill.amountS = BigInteger(it.asString)
                }

                jsonObj.get("amountB")?.let {
                    fill.amountB = BigInteger(it.asString)
                }

                jsonObj.get("tokenS")?.let {
                    fill.tokenS = it.asString
                }

                jsonObj.get("tokenB")?.let {
                    fill.tokenB = it.asString
                }

                jsonObj.get("lrcReward")?.let {
                    fill.lrcReward = BigInteger(it.asString)
                }

                jsonObj.get("lrcFee")?.let {
                    fill.lrcFee = BigInteger(it.asString)
                }

                jsonObj.get("splitS")?.let {
                    fill.splitS = BigInteger(it.asString)
                }

                jsonObj.get("splitB")?.let {
                    fill.splitB = BigInteger(it.asString)
                }

                jsonObj.get("market")?.let {
                    fill.market = it.asString
                }


                return fill
            }
        }

    }


}
