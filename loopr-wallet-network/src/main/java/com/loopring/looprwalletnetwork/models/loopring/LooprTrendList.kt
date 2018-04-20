package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprTrendList : RealmObject() {

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
     * A list of [LooprTrend] objects with trend data
     */
    var trends : RealmList<LooprTrend>? = null

    /**
     * Custom class deserializer
     */
    class LooprTrendListDeserializer : JsonDeserializer<LooprTrendList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTrendList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val trendsList = LooprTrendList()
                var trendsJsonObject = json.asJsonObject

                //TODO - check if this code is enough to handle normally encountered errors
                trendsJsonObject.get("id")?.let {
                    trendsList.id = it.asString.toIntOrNull()
                }

                trendsJsonObject.get("jsonrpc")?.let {
                    trendsList.jsonrpc  = it.asString
                }


                var trendsJsonArray = trendsJsonObject.get("result").asJsonObject.get("data").asJsonArray

                trendsList.trends = RealmList()
                trendsJsonArray.forEach {
                    trendsList.trends?.add(context.deserialize(it,LooprTrend::class.java))
                }

                return trendsList
            }
        }

    }



}