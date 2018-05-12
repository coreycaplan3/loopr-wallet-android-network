package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprUnlockResponse(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * Success or fail info
     * Example output - "unlock_notice_success"
     */
    @SerializedName("result")
    var response : String? = null

    /**
     * Custom class deserializer
     */
    class LooprUnlockResponseDeserializer : JsonDeserializer<LooprUnlockResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprUnlockResponse? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val unlockResponseObj = LooprUnlockResponse()

                LooprResponse.checkForError(jsonObj)
                unlockResponseObj.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    unlockResponseObj.response = it.asString
                }


                return unlockResponseObj
            }
        }

    }
}