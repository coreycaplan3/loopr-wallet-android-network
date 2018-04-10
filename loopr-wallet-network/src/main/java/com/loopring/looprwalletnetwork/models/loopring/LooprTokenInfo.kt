package com.loopring.looprwalletnetwork.models.loopring

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

open class LooprTokenInfo : RealmObject() {

    /**
     * String representing the token's symbol
     * Example output - "LRC"
     */
    var token : String? = null

    /**
     * Number of [token] tokens being held by the account in smallest unit
     * Example output - 0x000001234d
     */
    var balance: BigInteger?
        get() {
            return mBalance?.let { BigInteger(it, 16) }
        }
        set(value) {
            mBalance = value?.toString(16)
        }

    private var mBalance: String? = null

    /**
     * Number of [token] tokens the account has in allowance in smallest unit\
     * Example output - 0x0000001233a
     */
    var allowance: BigInteger?
        get() {
            return mAllowance?.let { BigInteger(it, 16) }
        }
        set(value) {
            mAllowance = value?.toString(16)
        }

    private var mAllowance: String? = null

    /**
     * Custom class deserializer
     */
    class LooprTokenInfoDeserializer : JsonDeserializer<LooprTokenInfo> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTokenInfo? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val tokenInfo = LooprTokenInfo()

                //TODO - check if this code is enough to handle normally encountered errors
                //if (!jsonObj.get("id").isJsonNull && jsonObj.get("id").isJsonPrimitive) {
                jsonObj.get("token")?.let {
                    tokenInfo.token = it.asString
                }
                //}


                jsonObj.get("balance")?.let {
                    tokenInfo.mBalance  = it.asString
                }

                jsonObj.get("allowance")?.let {
                    tokenInfo.mAllowance = it.asString
                }

                return tokenInfo
            }
        }

    }

}