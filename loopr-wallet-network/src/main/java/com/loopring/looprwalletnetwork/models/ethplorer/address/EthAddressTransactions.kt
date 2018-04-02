package com.loopring.looprwalletnetwork.models.ethplorer.address

import com.google.gson.*
import java.lang.reflect.Type

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * List of [EthAddressInfo] objects for a given address
 *
 * limit:   maximum number of operations [1 - 50, default = 10]
 *
 * showZeroValues:  show transactions with zero ETH value, default = 0
 *
 * ```
 * [
 *   {
 *     timestamp:       # operation timestamp
 *     from:            # source address (if two addresses involved),
 *     to:              # destination address (if two addresses involved),
 *     hash:            # transaction hash
 *     value:           # ETH value (as is, not reduced to a floating point value),
 *     input:           # input data
 *     success:         # true if transactions was completed, false if failed
 *   },
 * ]
 * ```
 *
 * @author arknw229
 */
class EthAddressTransactions(
        /**
         * List of [EthAddressInfo] objects for a given address
         */
        var transactions: MutableList<EthAddressTransactionInfo>? = null
) {
    /**
     * Deserializer for [EthAddressTransactions]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthAddressTransactionsDeserializer : JsonDeserializer<EthAddressTransactions> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthAddressTransactions? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonTransactions = json.asJsonArray
                val transactions = EthAddressTransactions()

                val transactionList = mutableListOf<EthAddressTransactionInfo>()

                jsonTransactions.forEach {
                    val gson = GsonBuilder().registerTypeAdapter(EthAddressTransactionInfo::class.java, EthAddressTransactionInfo.EthAddressTransactionInfoDeserializer()).serializeNulls().create()
                    transactionList.add(gson.fromJson<EthAddressTransactionInfo>(it, EthAddressTransactionInfo::class.java))
                }

                transactions.transactions = transactionList

                return transactions
            }
        }
    }
}
