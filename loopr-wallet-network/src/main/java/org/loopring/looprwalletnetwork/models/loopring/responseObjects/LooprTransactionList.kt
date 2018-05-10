package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprTransactionList(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

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
                val transactionListObj = LooprTransactionList()

                LooprResponse.checkForError(jsonObj)
                transactionListObj.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    it.asJsonObject.get("pageIndex")?.let {
                        transactionListObj.pageIndex  = it.asInt
                    }
                    it.asJsonObject.get("pageSize")?.let {
                        transactionListObj.pageSize  = it.asInt
                    }
                    it.asJsonObject.get("total")?.let {
                        transactionListObj.total  = it.asInt
                    }

                    val transactionJsonArray = it.asJsonObject.get("data").asJsonArray

                    transactionListObj.transactions = RealmList()
                    transactionJsonArray.forEach {
                        transactionListObj.transactions?.add(context.deserialize(it, LooprTransaction::class.java))
                    }
                }

                return transactionListObj
            }
        }

    }

}