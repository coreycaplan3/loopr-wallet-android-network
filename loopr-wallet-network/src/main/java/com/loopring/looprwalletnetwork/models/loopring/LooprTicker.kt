package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal

open class LooprTicker : RealmObject() {

    /**
     * Exchange the data comes from
     * Example output - "loopr" or "binance" or "" (when using getTicker instead of getTickers)
     */
    @SerializedName("exchange")
    var exchange : String? = null

    /**
     * The market the data comes from
     * Example output - "EOS-WETH"
     */
    @SerializedName("market")
    var market : String? = null

    /**
     * The 24hr highest price
     * Example output - 30384.2
     */
    var high: BigDecimal?
        get() {
            return mHigh?.let { BigDecimal(it) }
        }
        set(value) {
            mHigh = value?.toString()
        }

    private var mHigh : String? = null

    /**
     * The 24hr lowest price
     * Example output - 19283.2
     */
    var low: BigDecimal?
        get() {
            return mLow?.let { BigDecimal(it) }
        }
        set(value) {
            mLow = value?.toString()
        }

    private var mLow : String? = null

    /**
     * The newest dealt price
     * Example output - 28002.2
     */
    var last: BigDecimal?
        get() {
            return mLast?.let { BigDecimal(it) }
        }
        set(value) {
            mLast = value?.toString()
        }

    private var mLast : String? = null

    /**
     * The 24hr exchange volume
     * Example output - 1038
     */
    var vol: BigDecimal?
        get() {
            return mVol?.let { BigDecimal(it) }
        }
        set(value) {
            mVol = value?.toString()
        }

    private var mVol : String? = null

    /**
     * The 24hr exchange amount
     * Example output - 1003839.32
     */
    var amount: BigDecimal?
        get() {
            return mAmount?.let { BigDecimal(it) }
        }
        set(value) {
            mAmount = value?.toString()
        }

    private var mAmount : String? = null

    /**
     * The highest buy price in the depth
     * Example output - 122321
     */
    var buy: BigDecimal?
        get() {
            return mBuy?.let { BigDecimal(it) }
        }
        set(value) {
            mBuy = value?.toString()
        }

    private var mBuy : String? = null

    /**
     * The lowest sell price in the depth
     * Example output - 12388
     */
    var sell: BigDecimal?
        get() {
            return mSell?.let { BigDecimal(it) }
        }
        set(value) {
            mSell = value?.toString()
        }

    private var mSell : String? = null

    /**
     * The 24hr change percent of price
     * Example output - "-50.12%"
     */
    @SerializedName("change")
    var change : String? = null

    /**
     * Custom class deserializer
     */
    class LooprTickerDeserializer : JsonDeserializer<LooprTicker> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTicker? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val ticker = LooprTicker()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("exchange")?.let {
                    ticker.exchange = it.asString
                }
                //}

                jsonObj.get("market")?.let {
                    ticker.market  = it.asString
                }

                jsonObj.get("high")?.let {
                    ticker.mHigh  = it.asString
                }

                jsonObj.get("low")?.let {
                    ticker.mLow  = it.asString
                }

                jsonObj.get("vol")?.let {
                    ticker.mVol  = it.asString
                }

                jsonObj.get("amount")?.let {
                    ticker.mAmount  = it.asString
                }

                jsonObj.get("buy")?.let {
                    ticker.mBuy  = it.asString
                }

                jsonObj.get("sell")?.let {
                    ticker.mSell  = it.asString
                }

                jsonObj.get("change")?.let {
                    ticker.change  = it.asString
                }

                return ticker
            }
        }

    }

}