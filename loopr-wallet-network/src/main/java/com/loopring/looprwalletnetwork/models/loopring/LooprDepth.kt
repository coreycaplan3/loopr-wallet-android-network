package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprDepth : RealmObject() {

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
     * [LooprDepthList] of objects with information about the order
     */
    var buyDepth : LooprDepthList? = null

    /**
     * [LooprDepthList] of objects with information about the order
     */
    var sellDepth : LooprDepthList? = null

    /**
     * The market pair
     * Example output - "LRC-ETH"
     */
    @SerializedName("market")
    var market : String? = null

    /**
     * The loopring protocol version
     * Example output - "v1.2"
     */
    @SerializedName("contractVersion")
    var contractVersion : String? = null

    /**
     * Custom class deserializer
     */
    class LooprDepthDeserializer : JsonDeserializer<LooprDepth> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprDepth? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val depth = LooprDepth()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("id")?.let {
                    depth.id = it.asString.toIntOrNull()
                }
                //}

                jsonObj.get("jsonrpc")?.let {
                    depth.jsonrpc  = it.asString
                }

                var depthLists = jsonObj.get("result").asJsonObject.get("depth").asJsonObject

                depth.buyDepth =  context.deserialize(depthLists.get("buy").asJsonObject,LooprOrderItem::class.java)

                depth.sellDepth =  context.deserialize(depthLists.get("sell").asJsonObject,LooprOrderItem::class.java)

                jsonObj.get("result")?.asJsonObject?.get("market")?.let {
                    depth.market  = it.asString
                }

                jsonObj.get("result")?.asJsonObject?.get("contractVersion")?.let {
                    depth.contractVersion  = it.asString
                }


                return depth
            }
        }

    }
}