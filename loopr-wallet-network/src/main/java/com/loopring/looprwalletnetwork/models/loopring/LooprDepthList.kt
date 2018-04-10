package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

open class LooprDepthList : RealmObject() {

    //TODO - come back to this and figure out the best way to get a 2D array
    /*var list : List<List<BigInteger>>?
        get() {
            return mList?.let { BigInteger(it) }
        }
        set(value) {
            mList = value?.toString(16)
        }*/

    /**
     * List of buy or sell depths. Each depth pair is a price and quantity at that price
     * Example output - ["0x000313", "0x013"], ["0x1", "0x2"], ["0x000123", "0x12"]
     */
    var list : RealmList<RealmList<String>>? = null

    /**
     * Custom class deserializer
     */
    class LooprDepthListDeserializer : JsonDeserializer<LooprDepthList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprDepthList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val depthList = LooprDepthList()

                var depthJsonArray = json.asJsonArray

                depthList.list = RealmList()
                depthJsonArray.forEach {
                    var depthJsonArrayElement = it.asJsonArray
                    depthList.list?.add(RealmList())
                    depthJsonArrayElement.forEach {
                        //TODO - figure out if this is the best way to get depthList.list.size
                        depthList.list?.get(depthList.list?.size!!)?.add(it.asString)
                    }
                }

                return depthList
            }
        }

    }

}