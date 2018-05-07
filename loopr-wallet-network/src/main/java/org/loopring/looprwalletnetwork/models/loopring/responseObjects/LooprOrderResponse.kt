package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprOrderResponse(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * Order Hash
     * Example output - "0xc7756d5d556383b2f965094464bdff3ebe658f263f552858cc4eff4ed0aeafeb"
     */
    var orderHash : String? = null

    /**
     * Custom class deserializer
     */
    class LooprOrderResponseDeserializer : JsonDeserializer<LooprOrderResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprOrderResponse? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val responseObj = LooprOrderResponse()

                LooprResponse.checkForError(jsonObj)
                responseObj.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result").asJsonObject.get("orderHash")?.let {
                    responseObj.orderHash = it.asString
                }

                return responseObj
            }
        }

    }
}