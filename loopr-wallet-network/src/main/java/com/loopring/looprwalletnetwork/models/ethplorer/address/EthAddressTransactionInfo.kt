package com.loopring.looprwalletnetwork.models.ethplorer.address

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 * Information about a transaction perfomed by an address
 *
 * limit:   maximum number of operations [1 - 50, default = 10]
 * showZeroValues:  show transactions with zero ETH value, default = 0
 *
 * {
 *   timestamp:       # operation timestamp
 *   from:            # source address (if two addresses involved),
 *   to:              # destination address (if two addresses involved),
 *   hash:            # transaction hash
 *   value:           # ETH value (as is, not reduced to a floating point value),
 *   input:           # input data
 *   success:         # true if transactions was completed, false if failed
 * }
 *
 * @author arknw229
 */
class EthAddressTransactionInfo(
        /**
         * Timestamp of the transaction
         */
        var timestamp: Long? = null,

        /**
         * Who the transaction is from (if there is more than one address involved)
         */
        var from: String? = null,

        /**
         * Who the transaction is going to (if there is more than one address involved)
         */
        var to: String? = null,

        /**
         * Transaction hash
         */
        var hash: String? = null,

        /**
         * Value of the transaction
         */
        var value: BigDecimal? = null,

        /**
         * Input for the transaction
         * Will often be "0x" or null, is only used if a smart contract is involved
         */
        var input: String? = null,

        /**
         * Success or failure of the transaction
         */
        @SerializedName("success")
        var isSuccess: Boolean? = null

) {

    /**
     * Deserializer for [EthAddressTransactionInfo]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthAddressTransactionInfoDeserializer : JsonDeserializer<EthAddressTransactionInfo> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthAddressTransactionInfo? {
            if (json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val addressTransactions = EthAddressTransactionInfo()

                if (jsonObj.get("timestamp") != null && !jsonObj.get("timestamp").isJsonNull) {
                    try {
                        addressTransactions.timestamp = jsonObj.get("timestamp").asLong
                    } catch (e: UnsupportedOperationException) {
                        addressTransactions.timestamp = null
                    }
                }
                if (jsonObj.get("from") != null && !jsonObj.get("from").isJsonNull && jsonObj.get("from").asString != "") {
                    addressTransactions.from = jsonObj.get("from").asString
                }
                if (jsonObj.get("to") != null && !jsonObj.get("to").isJsonNull && jsonObj.get("to").asString != "") {
                    addressTransactions.to = jsonObj.get("to").asString
                }
                if (jsonObj.get("hash") != null && !jsonObj.get("hash").isJsonNull && jsonObj.get("hash").asString != "") {
                    addressTransactions.hash = jsonObj.get("hash").asString
                }
                if (jsonObj.get("value") != null && !jsonObj.get("value").isJsonNull) {
                    addressTransactions.value = BigDecimal(jsonObj.get("value").asString)
                }
                if (jsonObj.get("input") != null && !jsonObj.get("input").isJsonNull && jsonObj.get("input").asString != "" && jsonObj.get("input").asString != "0x") {
                    addressTransactions.input = jsonObj.get("input").asString
                }
                if (jsonObj.get("success") != null && !jsonObj.get("success").isJsonNull) {
                    try {
                        addressTransactions.isSuccess = jsonObj.get("success").asBoolean
                    } catch (e: UnsupportedOperationException) {
                        addressTransactions.isSuccess = null
                    }
                }

                return addressTransactions
            }
        }
    }
}
