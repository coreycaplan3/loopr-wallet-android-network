package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprTickerList(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * Ticker results from various exchanges
     */
    var tickers : RealmList<LooprTicker>? = null

    /**
     * Custom class deserializer
     */
    class LooprTickerListDeserializer : JsonDeserializer<LooprTickerList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTickerList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val tickerList = LooprTickerList()

                LooprResponse.checkForError(jsonObj)
                tickerList.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    val tickerJsonArray = it.asJsonArray

                    tickerList.tickers = RealmList()
                    tickerJsonArray.forEach {
                        tickerList.tickers?.add(context.deserialize(it, LooprTicker::class.java))
                    }
                }

                return tickerList

            }
        }

    }
}