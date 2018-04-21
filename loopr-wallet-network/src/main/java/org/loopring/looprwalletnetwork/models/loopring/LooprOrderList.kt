package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprOrderList : RealmObject() {

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
     * List of [LooprOrder] objects with information about the order
     */
    var orders : RealmList<LooprOrderItem>? = null

    /**
     * Total amount of orders
     * Example output - 12
     */
    @SerializedName("total")
    var total : Integer? = null

    /**
     * Index of page
     * Example output - 3
     */
    @SerializedName("pageIndex")
    var pageIndex : Integer? = null

    /**
     * Number of results per page
     * Example output - 10
     */
    @SerializedName("pageSize")
    var pageSize : Integer? = null

    /**
     * Custom class deserializer
     */
    class LooprOrderListDeserializer : JsonDeserializer<LooprOrderList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprOrderList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val orderList = LooprOrderList()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("id")?.let {
                    orderList.id = it.asString.toIntOrNull()
                }
                //}


                jsonObj.get("jsonrpc")?.let {
                    orderList.jsonrpc  = it.asString
                }

                var tokenJsonArray = jsonObj.get("result").asJsonObject.get("data").asJsonArray

                orderList.orders = RealmList()
                tokenJsonArray.forEach {
                    orderList.orders?.add(context.deserialize(it,LooprOrderItem::class.java))
                }

                return orderList
            }
        }

    }
}