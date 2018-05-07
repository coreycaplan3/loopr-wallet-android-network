package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.util.*

open class LooprTransactionSubmittedResponse(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * Possibly empty response
     * Example output -
     * TODO - find out if this ever returns anything
     */
    @SerializedName("result")
    var pairs : String? = null

    /**
     * Custom class deserializer
     */
    class LooprTransactionSubmittedResponseDeserializer : JsonDeserializer<LooprTransactionSubmittedResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTransactionSubmittedResponse? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val transSubmittedObj = LooprTransactionSubmittedResponse()

                LooprResponse.checkForError(jsonObj)
                transSubmittedObj.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    transSubmittedObj.pairs = it.asString
                }
                return transSubmittedObj
            }
        }

    }
}