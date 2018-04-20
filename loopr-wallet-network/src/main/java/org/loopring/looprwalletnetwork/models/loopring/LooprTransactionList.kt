package org.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprTransactionList : RealmObject() {

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
     * Transaction list
     */
    var transactions : RealmList<LooprTransaction>? = null

    /**
     * Page index
     * Example output - 1
     */
    var pageIndex : Int? = null

    /**
     * Page size
     * Example output - 20
     */
    var pageSize : Int? = null

    /**
     * Total number of results
     * Example output - 212
     */
    var total : Int? = null

    /**
     * Custom class deserializer
     */
    class LooprTransactionListDeserializer : JsonDeserializer<LooprTransactionList> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTransactionList? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val transactionList = LooprTransactionList()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("id")?.let {
                    transactionList.id = it.asString.toIntOrNull()
                }
                //}

                jsonObj.get("jsonrpc")?.let {
                    transactionList.jsonrpc  = it.asString
                }

                jsonObj.get("result").asJsonObject.get("pageIndex")?.let {
                    transactionList.pageIndex  = it.asInt
                }

                jsonObj.get("result").asJsonObject.get("pageSize")?.let {
                    transactionList.pageSize  = it.asInt
                }

                jsonObj.get("result").asJsonObject.get("total")?.let {
                    transactionList.total  = it.asInt
                }

                var transactionJsonArray = jsonObj.get("result").asJsonObject.get("data").asJsonArray

                transactionList.transactions = RealmList()
                transactionJsonArray.forEach {
                    transactionList.transactions?.add(context.deserialize(it,LooprTransaction::class.java))
                }

                return transactionList
            }
        }

    }

}