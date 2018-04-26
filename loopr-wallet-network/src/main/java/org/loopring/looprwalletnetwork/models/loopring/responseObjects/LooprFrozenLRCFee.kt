package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

open class LooprFrozenLRCFee : RealmObject() {

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
     * The frozen amount in hex format
     * Example output - ["0x2347ad6c"]
     * TODO - find out if this ever returns more than one string. If not deserialize straight to string
     */
    var frozenFees: RealmList<BigInteger>?
        get() {
            val response = RealmList<BigInteger>()
            val feesList = mFrozenFees //Have to get mFrozenFees at a moment in time in case of changes
            feesList?.let {
                for (element in feesList) {
                    response.add(BigInteger(element,10))
                }
            }
            return response
        }
        set(value) {
            mFrozenFees = RealmList()
            value?.let {
                for (element in value) {
                    mFrozenFees?.add(element.toString(10))
                }
            }
        }

    private var mFrozenFees : RealmList<String>? = null

    /**
     * Custom class deserializer
     */
    class LooprFrozenLRCFeeDeserializer : JsonDeserializer<LooprFrozenLRCFee> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprFrozenLRCFee? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val frozenFee = LooprFrozenLRCFee()
                val jsonObj = json.asJsonObject

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("id")?.let {
                    frozenFee.id = it.asString.toIntOrNull()
                }

                jsonObj.get("jsonrpc")?.let {
                    frozenFee.jsonrpc  = it.asString
                }

                jsonObj.get("result")?.let {
                    frozenFee.mFrozenFees = RealmList()
                    it.asJsonArray.forEach {
                        frozenFee.mFrozenFees?.add(BigInteger(it.asString,16).toString(10))
                    }
                }

                return frozenFee
            }
        }

    }
}