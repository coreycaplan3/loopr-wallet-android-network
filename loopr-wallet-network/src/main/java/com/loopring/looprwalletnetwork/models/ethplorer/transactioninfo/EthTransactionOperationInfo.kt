package com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import android.util.Log
import com.google.gson.*
import com.loopring.looprwalletnetwork.models.ethplorer.eth.EthTokenInfo
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Operations performed with an ERC20 token
 *
 * @author arknw229
 */
open class EthTransactionOperationInfo : RealmObject() {


    /**
     * Timestamp of the operation
     */
    var timestamp: Long? = null

    /**
     * Hash of the transaction
     */
    var transactionHash: String? = null

    /**
     * Ether value of the operation
     */
    var value: BigInteger?
        get() = mValue?.let { BigInteger(it) }
        set(value) {
            mValue = value?.toString()
        }

    private var mValue: String? = null

    /**
     *
     */
    var intValue: Int? = null

    /**
     * operation type (transfer, approve, issuance, mint, burn, etc)
     */
    var type: String? = null

    /**
     * Priority of the transaction
     */
    var priority: Int? = null

    /**
     * Source address (if two addresses involved)
     */
    var from: String? = null

    /**
     * Destination address (if two addresses involved)
     */
    var to: String? = null

    /**
     * Array of addresses involved in the transaction
     */
    var addresses: RealmList<String>? = null

    /**
     * Information about the ERC20 token involved
     */
    var tokenInfo: EthTokenInfo? = null

    /**
     * Deserializer for [EthTransactionOperationInfo]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthTransactionOperationInfoDeserializer : JsonDeserializer<EthTransactionOperationInfo> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthTransactionOperationInfo? {
            if (json.isJsonPrimitive || json.isJsonNull) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val operationInfo = EthTransactionOperationInfo()
                if (jsonObj.get("timestamp") != null && !jsonObj.get("timestamp").isJsonNull) {
                    try {
                        operationInfo.timestamp = jsonObj.get("timestamp").asLong
                    } catch (e: UnsupportedOperationException) {
                        Log.w("EthTransactionOpInfo", "Warning: json element 'timestamp' could not be parsed as a Long, variable set to null")
                        operationInfo.timestamp = null
                    }
                }
                // Check for empty string for no hash
                if (jsonObj.get("transactionHash") != null && !jsonObj.get("transactionHash").isJsonNull && jsonObj.get("transactionHash").asString != "") {
                    operationInfo.transactionHash = jsonObj.get("transactionHash").asString
                }
                if (jsonObj.get("value") != null && !jsonObj.get("value").isJsonNull && jsonObj.get("value").asString != "") {
                    try {
                        operationInfo.value = BigInteger(jsonObj.get("value").asString)
                    } catch (e: NumberFormatException) {
                        Log.w("EthTransactionOpInfo", "Warning: json element 'value' could not be parsed as a BigDecimal, variable set to null")
                        operationInfo.value = null
                    }
                }
                if (jsonObj.get("intValue") != null && !jsonObj.get("intValue").isJsonNull) {
                    try {
                        operationInfo.intValue = jsonObj.get("intValue").asInt
                    } catch (e: UnsupportedOperationException) {
                        Log.w("EthTransactionOpInfo", "Warning: json element 'intValue' could not be parsed as a Int, variable set to null")
                        operationInfo.intValue = null
                    }
                }
                // Check for empty string for no type
                if (jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull && jsonObj.get("type").asString != "") {
                    operationInfo.type = jsonObj.get("type").asString
                }
                if (jsonObj.get("priority") != null && !jsonObj.get("priority").isJsonNull) {
                    try {
                        operationInfo.priority = jsonObj.get("priority").asInt
                    } catch (e: UnsupportedOperationException) {
                        Log.w("EthTransactionOpInfo", "Warning: json element 'priority' could not be parsed as a Int, variable set to null")
                        operationInfo.priority = null
                    }
                }
                // Check for empty string for no from
                if (jsonObj.get("from") != null && !jsonObj.get("from").isJsonNull && jsonObj.get("from").asString != "") {
                    operationInfo.from = jsonObj.get("from").asString
                }
                // Check for empty string for no to
                if (jsonObj.get("to") != null && !jsonObj.get("to").isJsonNull && jsonObj.get("to").asString != "") {
                    operationInfo.to = jsonObj.get("to").asString
                }
                if (jsonObj.get("addresses") != null && !jsonObj.get("addresses").isJsonNull) {
                    val addressList = jsonObj.get("addresses").asJsonArray
                    val addresses = RealmList<String>()

                    addressList.forEach {
                        if (it != null && !it.isJsonNull && it.asString != "") {
                            addresses.add(it.asString)
                        }
                    }

                    operationInfo.addresses = addresses
                }
                if (jsonObj.get("tokenInfo") != null && !jsonObj.get("tokenInfo").isJsonNull && !jsonObj.get("tokenInfo").isJsonPrimitive) {
                    val gson = GsonBuilder().registerTypeAdapter(EthTokenInfo::class.java, EthTokenInfo.EthTokenInfoDeserializer()).serializeNulls().create()
                    operationInfo.tokenInfo = gson.fromJson(jsonObj.get("tokenInfo").asJsonObject, EthTokenInfo::class.java)
                }


                return operationInfo
            }
        }
    }
}
