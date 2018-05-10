package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal
import java.util.*

open class LooprTrend : RealmObject() {

    /**
     * The market type
     * Example output - "LRC-WETH"
     */
    @SerializedName("market")
    var market : String? = null

    /**
     * The 24hr highest price
     * Example output - 30384.2
     */
    var high : BigDecimal?
        get() {
            return mHigh?.let{ BigDecimal(it)}
        }
        set(value) {
            mHigh = value.toString()
        }

    private var mHigh : String? = null

    /**
     * The 24hr lowest price
     * Example output - 19283.2
     */
    var low : BigDecimal?
        get() {
            return mLow?.let{ BigDecimal(it)}
        }
        set(value) {
            mLow = value.toString()
        }

    private var mLow : String? = null

    /**
     * The 24hr exchange volume - refers to volume of token B in a A-B trading pair
     * Example output - 1038
     */
    var vol : BigDecimal?
        get() {
            return mVol?.let{ BigDecimal(it)}
        }
        set(value) {
            mVol = value.toString()
        }

    private var mVol : String? = null

    /**
     * The 24hr exchange amount - refers to volume of token A in a A-B trading pair
     * Example output - 1003839.32
     */
    var amount : BigDecimal?
        get() {
            return mAmount?.let{ BigDecimal(it)}
        }
        set(value) {
            mAmount = value.toString()
        }

    private var mAmount : String? = null

    /**
     * The opening price
     * Example output - 122321.01
     */
    var open : BigDecimal?
        get() {
            return mOpen?.let{ BigDecimal(it)}
        }
        set(value) {
            mOpen = value.toString()
        }

    private var mOpen : String? = null

    /**
     * The closing price
     * Example output - 12388.3
     */
    var close : BigDecimal?
        get() {
            return mClose?.let{ BigDecimal(it)}
        }
        set(value) {
            mClose = value.toString()
        }

    private var mClose : String? = null

    /**
     * The statistical cycle start time
     * Example output - 1512646617
     */
    @SerializedName("start")
    private var start : Date? = null

    /**
     * The statistical cycle end time
     * Example output - 1512646617
     */
    @SerializedName("end")
    private var end : Date? = null

    /**
     * The time the data was created
     * Example output - 1512646617
     */
    @SerializedName("createTime")
    private var createTime : Date? = null

    /**
     * Custom class deserializer
     */
    class LooprTrendDeserializer : JsonDeserializer<LooprTrend> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTrend? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val trendsList = LooprTrend()
                val trendsJsonObject = json.asJsonObject

                trendsJsonObject.get("market")?.let {
                    trendsList.market  = it.asString
                }

                trendsJsonObject.get("high")?.let {
                    trendsList.high  = BigDecimal(it.asString)
                }

                trendsJsonObject.get("low")?.let {
                    trendsList.low  = BigDecimal(it.asString)
                }

                trendsJsonObject.get("vol")?.let {
                    trendsList.vol  = BigDecimal(it.asString)
                }

                trendsJsonObject.get("amount")?.let {
                    trendsList.amount  = BigDecimal(it.asString)
                }

                trendsJsonObject.get("open")?.let {
                    trendsList.open  = BigDecimal(it.asString)
                }

                trendsJsonObject.get("close")?.let {
                    trendsList.close  = BigDecimal(it.asString)
                }

                trendsJsonObject.get("start")?.let {
                    trendsList.start  = Date(it.asLong)
                }

                trendsJsonObject.get("end")?.let {
                    trendsList.end  = Date(it.asLong)
                }

                trendsJsonObject.get("createTime")?.let {
                    trendsList.createTime  = Date(it.asLong)
                }

                return trendsList
            }
        }

    }

}