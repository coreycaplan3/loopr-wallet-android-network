package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprOrderResponse : RealmObject() {
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
     * Order Hash
     * Example output - "0xc7756d5d556383b2f965094464bdff3ebe658f263f552858cc4eff4ed0aeafeb"
     */
    var orderHash : String? = null

    /**
     * Custom class deserializer
     */
    class LooprOrderResponseDeserializer : JsonDeserializer<LooprOrderResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprOrderResponse? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val response = LooprOrderResponse()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("id")?.let {
                    response.id = it.asInt
                }
                //}


                jsonObj.get("jsonrpc")?.let {
                    response.jsonrpc  = it.asString
                }

                jsonObj.get("result").asJsonObject.get("orderHash")?.let {
                    response.orderHash = it.asString
                }

                return response
            }
        }

    }
}