package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprOrderList(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * List of [LooprOrder] objects with information about the order
     */
    var orders : RealmList<LooprOrderItem>? = null

    /**
     * Total amount of orders
     * Example output - 12
     */
    @SerializedName("total")
    var total : Int? = null

    /**
     * Index of page
     * Example output - 3
     */
    @SerializedName("pageIndex")
    var pageIndex : Int? = null

    /**
     * Number of results per page
     * Example output - 10
     */
    @SerializedName("pageSize")
    var pageSize : Int? = null

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

                LooprResponse.checkForError(jsonObj)
                orderList.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    orderList.total = it.asJsonObject.get("total").asString.toIntOrNull()
                    orderList.pageIndex = it.asJsonObject.get("pageIndex").asString.toIntOrNull()
                    orderList.pageSize = it.asJsonObject.get("pageSize").asString.toIntOrNull()

                    val dataArray = it.asJsonObject.get("data").asJsonArray
                    orderList.orders = RealmList()
                    dataArray.forEach {
                        orderList.orders?.add(context.deserialize(it, LooprOrderItem::class.java))
                    }
                }

                return orderList
            }
        }

    }
}