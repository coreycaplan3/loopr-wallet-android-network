package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprMinedRingList : RealmObject() {

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

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("id")?.let {
                    minedRingList.id = it.asString.toIntOrNull()
                }

                jsonObj.get("jsonrpc")?.let {
                    minedRingList.jsonrpc  = it.asString
                }

                jsonObj.get("total")?.let {
                    minedRingList.total  = it.asInt
                }

                jsonObj.get("pageIndex")?.let {
                    minedRingList.pageIndex  = it.asInt
                }

                jsonObj.get("pageSize")?.let {
                    minedRingList.pageSize  = it.asInt
                }

                jsonObj.get("result")?.let {
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