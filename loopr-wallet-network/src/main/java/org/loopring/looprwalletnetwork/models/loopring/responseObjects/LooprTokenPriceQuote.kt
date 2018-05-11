package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal

open class LooprTokenPriceQuote : RealmObject() {

    /**
     * The token the [price] data is for
     * Example output - "ETH"
     */
    @SerializedName("symbol")
    var symbol : String? = null

    /**
     * Price for the [token] in the currency stated in the [LooprPriceQuote] object
     * Example output - 31022.12
     */
    var price: BigDecimal?
        get() {
            return mPrice?.let { BigDecimal(it) }
        }
        set(value) {
            mPrice = value.toString()
        }

    private var mPrice : String? = null

    /**
     * Custom class deserializer
     */
    class LooprTokenPriceQuoteDeserializer : JsonDeserializer<LooprTokenPriceQuote> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTokenPriceQuote? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val priceQuote = LooprTokenPriceQuote()
                val jsonObj = json.asJsonObject

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("symbol")?.let {
                    priceQuote.symbol = it.asString
                }

                jsonObj.get("price")?.let {
                    priceQuote.mPrice  = it.asString
                }

                return priceQuote
            }
        }

    }

}