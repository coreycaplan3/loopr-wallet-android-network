package org.loopring.looprwalletnetwork.models.loopring.responseObjects

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
     * Example output - "LRC"
     */
    @SerializedName("tokenS")
    var toSell : String? = null

    /**
     * Token to buy
     * Example output - "APPC"
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
     * The market pair for the order
     * Example output - "LRC-WETH"
     */
    @SerializedName("market")
    var market : String? = null

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
     * Address of the wallet
     * Example output - "0xb94065482Ad64d4c2b9252358D746B39e820A582"
     */
    @SerializedName("walletAddress")
    var walletAddress : String? = null

    /**
     * The wallet auth public key
     * Example output - "0xb94065482Ad64d4c2b9252358D746B39e820A582"
     */
    @SerializedName("authAddr")
    var authAddr : String? = null

    /**
     * The wallet auth private key to sign ring when submitting ring
     * Example output - "0xe84989447467e438565dd2715d93d7537e9bc07fe7dc3044d8cbf4bd10967a69"
     */
    @SerializedName("authPrivateKey")
    var authPrivateKey : String? = null

    /**
     * The buy or sell side
     * Example output - "buy"
     */
    @SerializedName("side")
    var side : String? = null

    /**
     * The time the order was created
     * Example output - 1526263146
     */
    @SerializedName("createTime")
    var createTime : Date? = null

    /**
     * The type of order
     * Example output - "market_order"
     */
    @SerializedName("orderType")
    var orderType : String? = null

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

                jsonObj.get("market")?.let {
                    order.market = it.asString
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

                jsonObj.get("walletAddress")?.let {
                    order.walletAddress = it.asString
                }

                jsonObj.get("authAddr")?.let {
                    order.authAddr = it.asString
                }

                jsonObj.get("authPrivateKey")?.let {
                    order.authPrivateKey = it.asString
                }

                jsonObj.get("side")?.let {
                    order.side = it.asString
                }

                jsonObj.get("createTime")?.let {
                    order.createTime = context.deserialize(it,Date::class.java)
                }

                jsonObj.get("orderType")?.let {
                    order.orderType = it.asString
                }

                return order
            }
        }

    }

}