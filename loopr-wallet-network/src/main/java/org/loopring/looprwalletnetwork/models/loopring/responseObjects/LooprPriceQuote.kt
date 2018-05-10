package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprPriceQuote(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

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
                val jsonObj = json.asJsonObject
                val priceQuote = LooprPriceQuote()

                LooprResponse.checkForError(jsonObj)
                priceQuote.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    it.asJsonObject.get("currency")?.let {
                        priceQuote.currency  = it.asString
                    }

                    val tokensJsonArray = it.asJsonObject.get("tokens").asJsonArray

                    priceQuote.tokens = RealmList()
                    tokensJsonArray.forEach {
                        priceQuote.tokens?.add(context.deserialize(it, LooprTokenPriceQuote::class.java))
                    }
                }

                return priceQuote
            }
        }

    }

}