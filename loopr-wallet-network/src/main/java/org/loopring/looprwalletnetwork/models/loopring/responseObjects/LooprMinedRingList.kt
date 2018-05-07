package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprMinedRingList(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * List of [LooprMinedRing] objects with mined ring data
     */
    var minedRings : RealmList<LooprMinedRing>? = null

    /**
     * Total amount of orders
     * Example output - 12
     */
    var total : Int? = null

    /**
     * Index of page
     * Example output - 2
     */
    var pageIndex : Int? = null

    /**
     * Amount per page
     * Example output - 10
     */
    var pageSize : Int? = null

    /**
     * Custom class deserializer
     */
    class LooprMinedRingListDeserializer : JsonDeserializer<LooprMinedRingList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprMinedRingList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val minedRingList = LooprMinedRingList()

                LooprResponse.checkForError(jsonObj)
                minedRingList.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    it.asJsonObject.get("total")?.let {
                        minedRingList.total  = it.asInt
                    }

                    it.asJsonObject.get("pageIndex")?.let {
                        minedRingList.pageIndex  = it.asInt
                    }

                    it.asJsonObject.get("pageSize")?.let {
                        minedRingList.pageSize  = it.asInt
                    }

                    val minedRingJsonArray = it.asJsonObject.get("data").asJsonArray

                    minedRingList.minedRings = RealmList()
                    minedRingJsonArray.forEach {
                        minedRingList.minedRings?.add(context.deserialize(it, LooprMinedRing::class.java))
                    }
                }

                return minedRingList
            }
        }

    }
}