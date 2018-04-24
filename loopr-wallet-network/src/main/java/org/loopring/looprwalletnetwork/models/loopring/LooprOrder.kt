package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger
import java.util.*

//TODO - this class has conflicting documentation. It shows using timestamp and ttl in the "loopring_getOrders" response, but validSince and validUntil in loopring_submitOrder and the solidity code
open class LooprOrder : RealmObject() {

    /**
     * Order protocol
     * Example output - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("protocol")
    var protocol : String? = null

    /**
     * Delegate Address
     * Example output - "0x5567ee920f7E62274284985D793344351A00142B"
     */
    @SerializedName("delegateAddress")
    var delegateAddress : String? = null

    /**
     * Order owner
     * Example output - "0x847983c3a34afa192cfee860698584c030f4c9db1"
     */
    @SerializedName("owner")
    var owner : String? = null

    /**
     * Token to sell
     * Example output - "0x2956356cd2a2bf3202f771f50d3d14a367b48070"
     */
    @SerializedName("tokenS")
    var toSell : String? = null

    /**
     * Token to buy
     * Example output - "0xef68e7c694f40c8202821edf525de3782458639f"
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
    var validUntil : Date? = null

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

    private var mLrcFee : String? = null

    /**
     * If true, this order does not accept buying more than [toBuy]
     * Example output - true
     */
    @SerializedName("buyNoMoreThanAmountB")
    var buyNoMoreThanBuyAmt : Boolean? = null

    /**
     * The percentage of savings paid to miner (0-100)
     * Example output - 50 (50% paid to miner)
     */
    @SerializedName("marginSplitPercentage")
    var marginSplitPercentage : Int? = null

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

    /**
     * Custom class deserializer
     */
    class LooprOrderDeserializer : JsonDeserializer<LooprOrder> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprOrder? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val order = LooprOrder()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                //TODO - find out if you can just use context.deserialize() for all strings and ints. See through testing if it will handle edge cases
                jsonObj.get("protocol")?.let {
                    order.protocol = it.asString
                }
                //}

                jsonObj.get("delegateAddress")?.let {
                    order.delegateAddress = it.asString
                }

                jsonObj.get("owner")?.let {
                    order.owner = it.asString
                }

                jsonObj.get("tokenS")?.let {
                    order.toSell = it.asString
                }

                jsonObj.get("tokenB")?.let {
                    order.toBuy = it.asString
                }

                jsonObj.get("amountS")?.let {
                    order.mAmtToSell = it.asString
                }

                jsonObj.get("amountB")?.let {
                    order.mAmtToBuy = it.asString
                }

                jsonObj.get("validSince")?.let {
                    order.validSince = context.deserialize(it, Date::class.java)
                }

                jsonObj.get("validUntil")?.let {
                    order.validUntil = context.deserialize(it, Date::class.java)
                }

                jsonObj.get("lrcFee")?.let {
                    order.mLrcFee = it.asString
                }

                jsonObj.get("lrcFee")?.let {
                    order.mLrcFee = it.asString
                }

                jsonObj.get("buyNoMoreThanAmountB")?.let {
                    order.buyNoMoreThanBuyAmt = it.asBoolean
                }

                jsonObj.get("marginSplitPercentage")?.let {
                    order.marginSplitPercentage = it.asInt
                }

                jsonObj.get("v")?.let {
                    order.v = it.asString
                }

                jsonObj.get("r")?.let {
                    order.r = it.asString
                }

                jsonObj.get("s")?.let {
                    order.s = it.asString
                }

                return order
            }
        }

    }

}