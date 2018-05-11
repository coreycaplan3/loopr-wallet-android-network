package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

open class LooprFrozenLRCFee(
        override var id: Int? = null, override var jsonrpc: String? = null
) : RealmObject(), LooprResponse {

    /**
     * The frozen amount in hex format
     * Example output - "0x2347ad6c"
     */
    var frozenFees: BigInteger?
        get() {
            return mFrozenFees?.let { BigInteger(it,16) }
        }
        set(value) {
            mFrozenFees = value?.toString(16)
        }

    private var mFrozenFees : String? = null

    /**
     * Custom class deserializer
     */
    class LooprFrozenLRCFeeDeserializer : JsonDeserializer<LooprFrozenLRCFee> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprFrozenLRCFee? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val frozenFee = LooprFrozenLRCFee()

                LooprResponse.checkForError(jsonObj)
                frozenFee.setIdJsonRPC(jsonObj)

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("result")?.let {
                    frozenFee.mFrozenFees = it.asString.substring(2)
                }

                return frozenFee
            }
        }

    }
}