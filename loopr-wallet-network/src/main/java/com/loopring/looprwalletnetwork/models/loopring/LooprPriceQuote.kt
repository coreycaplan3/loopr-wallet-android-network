package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprPriceQuote : RealmObject() {

    /**
     * TODO - figure out what this id is
     * Example output - 64
     */
    @SerializedName("id")
    var id : Int?  = null

    /**
     * String representing the version of jsonrpc. Should match the one used in the request
     * Example output - "2.0"
     */
    @SerializedName("jsonrpc")
    var jsonrpc : String? = null

    /**
     * The base currency, CNY or USD
     * Example output - "CNY"
     */
    @SerializedName("currency")
    var currency : String? = null

    /**
     * Every token price int the currency
     */
    var tokens : RealmList<LooprTokenPriceQuote>? = null

    /**
     * Custom class deserializer
     */
    class LooprPriceQuoteDeserializer : JsonDeserializer<LooprPriceQuote> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprPriceQuote? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val priceQuote = LooprPriceQuote()
                var priceQuoteJsonObject = json.asJsonObject

                //TODO - check if this code is enough to handle normally encountered errors
                priceQuoteJsonObject.get("id")?.let {
                    priceQuote.id = it.asString.toIntOrNull()
                }

                priceQuoteJsonObject.get("jsonrpc")?.let {
                    priceQuote.jsonrpc  = it.asString
                }

                priceQuoteJsonObject.get("result").asJsonObject.get("currency")?.let {
                    priceQuote.currency  = it.asString
                }

                var tokensJsonArray = priceQuoteJsonObject.get("result").asJsonObject.get("tokens").asJsonArray

                priceQuote.tokens = RealmList()
                tokensJsonArray.forEach {
                    priceQuote.tokens?.add(context.deserialize(it,LooprPriceQuote::class.java))
                }

                return priceQuote
            }
        }

    }

}