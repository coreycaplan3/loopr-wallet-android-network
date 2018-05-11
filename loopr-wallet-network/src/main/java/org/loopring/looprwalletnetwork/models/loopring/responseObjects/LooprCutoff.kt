package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.util.*

open class LooprCutoff(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * The cutoff timestamp string
     * Example output - "1501232222"
     */
    @SerializedName("result")
    var cutoff : Date? = null

    /**
     * Custom class deserializer
     */
    class LooprCutoffDeserializer : JsonDeserializer<LooprCutoff> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprCutoff? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val cutoffObj = LooprCutoff()

                LooprResponse.checkForError(jsonObj)
                cutoffObj.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    if (it.asString != "0") {
                        cutoffObj.cutoff = context.deserialize(it, Date::class.java)
                    }
                }
                return cutoffObj
            }
        }
    }

    companion object {
        val BLOCK_EARLIEST = "earliest"
        val BLOCK_LATEST = "latest"
        val BLOCK_PENDING = "pending"
    }

}