package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

class LooprTickerList : RealmObject() {

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
    class EthTokenInfoDeserializer : JsonDeserializer<LooprTickerList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTickerList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val tickerList = LooprTickerList()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("id")?.let {
                    tickerList.id = it.asString.toIntOrNull()
                }
                //}

                jsonObj.get("jsonrpc")?.let {
                    tickerList.jsonrpc  = it.asString
                }


                var tickerJsonArray = jsonObj.get("result").asJsonArray

                tickerList.tickers = RealmList()
                tickerJsonArray.forEach {
                    tickerList.tickers?.add(context.deserialize(it,LooprTicker::class.java))
                }

                return tickerList
            }
        }

    }
}