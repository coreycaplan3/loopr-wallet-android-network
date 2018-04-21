package org.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import org.loopring.looprwalletnetwork.utilities.ifNotNullOrEmpty
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal
import java.util.*

/**
 * Created by arknw229 on 3/12/18.
 *
 * Retrieve price data for currencies
 *
 * @author arknw229
 */
open class CoinPriceData : RealmObject() {

    /**
     * Price of the coin in [currency]. The scale is of size 2, so $100 is formatted as
     * *100.00*.
     */
    var rate: BigDecimal?
        get() = mRate?.let { BigDecimal(it) }
        set(value) {
            mRate = value?.toPlainString()
        }

    @SerializedName("rate")
    private var mRate: String? = null

    /**
     * How the price has changed over a 1 day period. 50% is saved as 50.00
     */
    var diff: BigDecimal?
        get() = mDiff?.let { BigDecimal(it) }
        set(value) {
            mDiff = value?.toPlainString()
        }

    @SerializedName("diff")
    private var mDiff: String? = null

    /**
     * How the price has changed over a 7 day period. 50% is saved as 50.00
     */
    var diff7d: BigDecimal?
        get() = mDiff7d?.let { BigDecimal(it) }
        set(value) {
            mDiff7d = value?.toPlainString()
        }

    @SerializedName("diff7d")
    private var mDiff7d: String? = null

    /**
     * Timestamp of when the data was retrieved
     */
    var ts: Date? = null

    /**
     * Market capitalization of the coin. IE 209,300,726.0 is formatted as 209300726.0
     */
    var marketCapUsd: BigDecimal?
        get() = mMarketCapUsd?.let { BigDecimal(it) }
        set(value) {
            mMarketCapUsd = value?.toPlainString()
        }

    @SerializedName("marketCapUsd")
    private var mMarketCapUsd: String? = null

    /**
     * Available supply of the coin. IE 572074043 is formatted as 572074043.0
     */
    var availableSupply: BigDecimal?
        get() = mAvailableSupply?.let {
            BigDecimal(it)
        }
        set(value) {
            mAvailableSupply = value?.toPlainString()
        }

    @SerializedName("availableSupply")
    private var mAvailableSupply: String? = null

    /**
     * 24 hour volume of the coin. IE 1668920 is formatted as 1668920.0
     */
    var volume24h: BigDecimal?
        get() = mVolume24h?.let { BigDecimal(it) }
        set(value) {
            mVolume24h = value?.toPlainString()
        }

    @SerializedName("volume24h")
    private var mVolume24h: String? = null

    /**
     * Currency that that is being used for the rest of this data
     */
    var currency: String? = null

    /**
     * Deserializer for [CoinPriceData]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class CoinPriceDataDeserializer : JsonDeserializer<CoinPriceData> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): CoinPriceData? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            }

            val jsonObj = json.asJsonObject
            val priceData = CoinPriceData()


            jsonObj.ifNotNullOrEmpty(CoinPriceData::rate) {
                priceData.mRate = jsonObj.get(it).asString
            }
            jsonObj.ifNotNullOrEmpty(CoinPriceData::diff) {
                priceData.mDiff = jsonObj.get(it).asString
            }
            jsonObj.ifNotNullOrEmpty(CoinPriceData::diff7d) {
                priceData.mDiff7d = jsonObj.get(it).asString
            }

            priceData.ts = context.deserialize(jsonObj.get(CoinPriceData::ts.name), Date::class.java)

            jsonObj.ifNotNullOrEmpty(CoinPriceData::marketCapUsd) {
                priceData.mMarketCapUsd = jsonObj.get(it).asString
            }
            jsonObj.ifNotNullOrEmpty(CoinPriceData::availableSupply) {
                priceData.mAvailableSupply = jsonObj.get(it).asString
            }
            jsonObj.ifNotNullOrEmpty(CoinPriceData::volume24h) {
                priceData.mVolume24h = jsonObj.get(it).asString
            }
            jsonObj.ifNotNullOrEmpty(CoinPriceData::currency) {
                priceData.currency = jsonObj.get(it).asString
            }

            return priceData
        }

    }
}