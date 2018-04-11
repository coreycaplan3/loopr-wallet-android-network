package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import java.lang.reflect.Type


class LooprTickerExchangeList {

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
     * Ticker results from various exchanges
     */
    var tickers : RealmList<LooprTicker>? = null

    /**
     * Custom class deserializer
     */
    class LooprTickerExchangeListDeserializer : JsonDeserializer<LooprTickerExchangeList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTickerExchangeList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val tickerList = LooprTickerExchangeList()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("id")?.let {
                    tickerList.id = it.asString.toIntOrNull()
                }
                //}

                jsonObj.get("jsonrpc")?.let {
                    tickerList.jsonrpc  = it.asString
                }


                var tickerJsonObject = jsonObj.get("result").asJsonObject

                tickerList.tickers = RealmList()

                //TODO - this one is missing 'market' so LooprTicker may fail deserialization. Find out through testing
                for (exchange in tickerJsonObject.keySet()) {
                    tickerList.tickers?.add(context.deserialize(tickerJsonObject.get(exchange).asJsonObject,LooprTicker::class.java))
                }

                return tickerList
            }
        }

    }
}