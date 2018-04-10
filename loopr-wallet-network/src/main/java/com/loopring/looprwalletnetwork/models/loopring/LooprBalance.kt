package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

open class LooprBalance : RealmObject() {

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
     * Version of the LoopringContract. Should match the one used in the request
     * Example output - "v1.0"
     */
    var contractVersion : String? = null

    /**
     * List of [LooprTokenInfo] objects with information about the token
     */
    var tokens : RealmList<LooprTokenInfo>? = null

    /**
     * Custom class deserializer
     */
    class EthTokenInfoDeserializer : JsonDeserializer<LooprBalance> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprBalance? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val balanceInfo = LooprBalance()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                    jsonObj.get("id")?.let {
                        balanceInfo.id = it.asString.toIntOrNull()
                    }
                //}


                jsonObj.get("jsonrpc")?.let {
                    balanceInfo.jsonrpc  = it.asString
                }

                jsonObj.get("result").asJsonObject.get("contractVersion")?.let {
                    balanceInfo.contractVersion = it.asString
                }

                var tokenJsonArray = jsonObj.get("result").asJsonObject.get("tokens").asJsonArray

                balanceInfo.tokens = RealmList()
                tokenJsonArray.forEach {
                    balanceInfo.tokens?.add(context.deserialize(it,LooprTokenInfo::class.java))
                }

                return balanceInfo
            }
        }

    }
}