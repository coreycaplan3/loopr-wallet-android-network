package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.lang.reflect.Type
import java.math.BigInteger
import java.util.*

open class LooprSupportedToken : RealmObject() {

    //TODO - expand documentation for each of these

    /**
     * Protocol
     * Example output - "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07"
     */
    @SerializedName("protocol")
    var protocol : String? = null

    /**
     * Symbol
     * Example output - "OMG"
     */
    @PrimaryKey
    @SerializedName("symbol")
    var symbol : String? = null

    /**
     * Source
     * Example output - "omisego"
     */
    @SerializedName("source")
    var source : String? = null

    /**
     * Source
     * Example output - 1524614471
     */
    @SerializedName("time")
    var time : Date? = null

    /**
     * Source
     * Example output - false
     */
    @SerializedName("deny")
    var deny : Boolean? = null

    /**
     * Number of decimals the token has
     * Example output - 1000000000000000000
     */
    var decimals : BigInteger?
        get() {
            return mDecimals?.let { BigInteger(it) }
        }
        set(value) {
            mDecimals = value?.toString()
        }

    private var mDecimals : String? = null

    /**
     * isMarket
     * Example output - false
     */
    @SerializedName("isMarket")
    var isMarket : Boolean? = null

    /**
     * isMarket
     * Example output - "1/8000"
     */
    @SerializedName("icoPrice")
    var icoPrice : String? = null

    /**
     * Custom class deserializer
     */
    class LooprSupportedTokenDeserializer : JsonDeserializer<LooprSupportedToken> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprSupportedToken? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val supportedTokenObj = LooprSupportedToken()

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("protocol")?.let {
                    supportedTokenObj.protocol = it.asString
                }

                jsonObj.get("symbol")?.let {
                    supportedTokenObj.symbol = it.asString
                }

                jsonObj.get("source")?.let {
                    supportedTokenObj.source = it.asString
                }

                jsonObj.get("time")?.let {
                    supportedTokenObj.time = Date(it.asString.toLong())
                }

                jsonObj.get("deny")?.let {
                    supportedTokenObj.deny = it.asBoolean
                }

                jsonObj.get("decimals")?.let {
                    supportedTokenObj.mDecimals = it.asString
                }

                jsonObj.get("isMarket")?.let {
                    supportedTokenObj.isMarket = it.asBoolean
                }

                jsonObj.get("icoPrice")?.let {
                    if (!it.isJsonNull) {
                        supportedTokenObj.icoPrice = it.asString
                    }
                }

                return supportedTokenObj
            }
        }

    }

}