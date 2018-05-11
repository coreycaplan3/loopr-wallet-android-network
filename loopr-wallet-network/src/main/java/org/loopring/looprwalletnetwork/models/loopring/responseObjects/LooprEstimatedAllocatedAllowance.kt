package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

open class LooprEstimatedAllocatedAllowance(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {


    /**
     * Allocated allowance amount
     * Example output - ["0x2347ad6c"]
     */
    var allowance: BigInteger?
        get() {
            return mAllowance?.let { BigInteger(it, 16) }
        }
        set(value) {
            mAllowance = value?.toString(16)
        }

    private var mAllowance : String? = null

    /**
     * Custom class deserializer
     */
    class LooprEstimatedAllocatedAllowanceDeserializer : JsonDeserializer<LooprEstimatedAllocatedAllowance> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprEstimatedAllocatedAllowance? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val allocatedAllowance = LooprEstimatedAllocatedAllowance()

                LooprResponse.checkForError(jsonObj)
                allocatedAllowance.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    allocatedAllowance.mAllowance = it.asString.substring(2)
                }

                return allocatedAllowance
            }
        }

    }
}