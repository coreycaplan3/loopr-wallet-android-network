package com.loopring.looprwalletnetwork.models.etherscan

import com.google.gson.*
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/12/18.
 *
 * Retrieve price data for currencies
 *
 * @author arknw229
 */
class CoinPriceData(
        /**
         * Price of the coin in [currency]
         */
        var rate: BigDecimal? = null,

        /**
         * How the price has changed over a 1 day period
         */
        var diff: BigDecimal? = null,

        /**
         * How the price has changed over a 7 day period
         */
        var diff7d: BigDecimal? = null,

        /**
         * Timestamp of when the data was retrieved
         */
        var ts: BigDecimal? = null,

        /**
         * Market capitalization of the coin
         */
        var marketCapUsd: BigDecimal? = null,

        /**
         * Available supply of the coin
         */
        var availableSupply: BigDecimal? = null,

        /**
         * 24 hour volume of the coin
         */
        var volume24h: BigDecimal? = null,

        /**
         * Currency that that is being used for the rest of this data
         */
        var currency: String? = null
) {
    /**
     * Deserializer for [CoinPriceData]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class CoinPriceDataDeserializer : JsonDeserializer<CoinPriceData> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): CoinPriceData? {

            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val priceData = CoinPriceData()


                jsonObj.ifNotNullOrEmpty("rate") {
                    priceData.rate = BigDecimal(jsonObj.get("rate").asString)
                }
                jsonObj.ifNotNullOrEmpty("diff") {
                    priceData.diff = BigDecimal(jsonObj.get("diff").asString)
                }
                jsonObj.ifNotNullOrEmpty("diff7d") {
                    priceData.rate = BigDecimal(jsonObj.get("diff7d").asString)
                }
                jsonObj.ifNotNullOrEmpty("ts") {
                    priceData.ts = BigDecimal(jsonObj.get("ts").asString)
                }
                jsonObj.ifNotNullOrEmpty("marketCapUsd") {
                    priceData.marketCapUsd = BigDecimal(jsonObj.get("marketCapUsd").asString)
                }
                jsonObj.ifNotNullOrEmpty("availableSupply") {
                    priceData.availableSupply = BigDecimal(jsonObj.get("availableSupply").asString)
                }
                jsonObj.ifNotNullOrEmpty("volume24h") {
                    priceData.volume24h = BigDecimal(jsonObj.get("volume24h").asString)
                }
                jsonObj.ifNotNullOrEmpty("currency") {
                    priceData.currency = jsonObj.get("currency").asString
                }

                return priceData
            }
        }

        /**
         * Executes the given [block] if the JSON key ([element]) is not null or empty
         */
        private inline fun JsonObject.ifNotNullOrEmpty(element: String, block: () -> Unit) {
            if (get(element) != null && get(element).isJsonPrimitive && get(element).asString.trim() != "") {
                block()
            }
        }

    }
}