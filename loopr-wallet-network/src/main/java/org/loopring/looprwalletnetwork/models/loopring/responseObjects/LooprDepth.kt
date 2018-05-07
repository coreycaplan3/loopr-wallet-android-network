package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprDepth(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

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
                val depthObj = LooprDepth()

                LooprResponse.checkForError(jsonObj)
                depthObj.setIdJsonRPC(jsonObj)

                jsonObj.get("result")?.let {
                    val depthLists = it.asJsonObject.get("depth").asJsonObject

                    depthLists.get("buy")?.let {
                        depthObj.buyDepth = RealmList()
                        it.asJsonArray.forEach {
                            depthObj.buyDepth?.add(context.deserialize(it, LooprDepthListItem::class.java))
                        }
                    }

                    depthLists.get("sell")?.let {
                        depthObj.sellDepth = RealmList()
                        it.asJsonArray.forEach {
                            depthObj.sellDepth?.add(context.deserialize(it, LooprDepthListItem::class.java))
                        }
                    }

                    it.asJsonObject?.get("market")?.let {
                        depthObj.market = it.asString
                    }

                    it.asJsonObject?.get("delegateAddress")?.let {
                        depthObj.delegateAddress = it.asString
                    }
                }


                return depthObj
            }
        }

    }
}