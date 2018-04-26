package org.loopring.looprwalletnetwork.models.loopring.responseObjects

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
     * List of [LooprDepthListItem] each with information about the buy demand at certain price depths
     */
    var buyDepth : RealmList<LooprDepthListItem>? = null

    /**
     * List of [LooprDepthListItem] each with information about the sell demand at certain price depths
     */
    var sellDepth : RealmList<LooprDepthListItem>? = null

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
    @SerializedName("delegateAddress")
    var delegateAddress : String? = null

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
                    depth.jsonrpc = it.asString
                }

                jsonObj.get("result")?.let {
                    val depthLists = it.asJsonObject.get("depth").asJsonObject

                    depthLists.get("buy")?.let {
                        depth.buyDepth = RealmList()
                        it.asJsonArray.forEach {
                            depth.buyDepth?.add(context.deserialize(it, LooprDepthListItem::class.java))
                        }
                    }

                    depthLists.get("sell")?.let {
                        depth.sellDepth = RealmList()
                        it.asJsonArray.forEach {
                            depth.sellDepth?.add(context.deserialize(it, LooprDepthListItem::class.java))
                        }
                    }

                    it.asJsonObject?.get("market")?.let {
                        depth.market = it.asString
                    }

                    it.asJsonObject?.get("delegateAddress")?.let {
                        depth.delegateAddress = it.asString
                    }
                }


                return depth
            }
        }

    }
}