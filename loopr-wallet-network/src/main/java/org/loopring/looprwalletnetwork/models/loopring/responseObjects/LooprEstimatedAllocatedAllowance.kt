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

open class LooprEstimatedAllocatedAllowance : RealmObject() {

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
     * TODO - check if this ever returns more than one list item, if not then go straight to string
     */
    var allowance: RealmList<BigInteger>?
        get() {
            val response = RealmList<BigInteger>()
            val allowanceList = mAllowance //Have to get mAllowance at a moment in time in case of changes
            allowanceList?.let {
                for (element in allowanceList) {
                    response.add(BigInteger(element,10))
                }
            }
            return response
        }
        set(value) {
            mAllowance = RealmList()
            value?.let {
                for (element in value) {
                    mAllowance?.add(element.toString(10))
                }
            }
        }

    private var mAllowance : RealmList<String>? = null

    /**
     * Custom class deserializer
     */
    class LooprEstimatedAllocatedAllowanceDeserializer : JsonDeserializer<LooprEstimatedAllocatedAllowance> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprEstimatedAllocatedAllowance? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val allocatedAllowance = LooprEstimatedAllocatedAllowance()
                val jsonObj = json.asJsonObject

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("id")?.let {
                    allocatedAllowance.id = it.asString.toIntOrNull()
                }

                jsonObj.get("jsonrpc")?.let {
                    allocatedAllowance.jsonrpc  = it.asString
                }

                jsonObj.get("result")?.let {
                    allocatedAllowance.mAllowance = RealmList()
                    it.asJsonArray.forEach {
                        allocatedAllowance.mAllowance?.add(BigInteger(it.asString,16).toString(10))
                    }
                }

                return allocatedAllowance
            }
        }

    }
}