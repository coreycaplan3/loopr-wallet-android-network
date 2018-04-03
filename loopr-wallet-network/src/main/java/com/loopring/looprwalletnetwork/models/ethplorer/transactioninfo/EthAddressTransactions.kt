package com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.reflect.Type

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * List of [EthAddressTransactionInfo] objects for a given address
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
open class EthAddressTransactions(

        /**
         * List of [EthAddressTransactionInfo] objects for a given address
         */
        var transactions: RealmList<EthAddressTransactionInfo>? = null
) : RealmObject() {

    /**
     * Deserializer for [EthAddressTransactions]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthAddressTransactionsDeserializer : JsonDeserializer<EthAddressTransactions> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthAddressTransactions? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            }

            val jsonArray = json.asJsonArray
            val transactions = EthAddressTransactions()

            transactions.transactions = RealmList()
            jsonArray.forEach {
                val transactionInfo: EthAddressTransactionInfo = context.deserialize(it, EthAddressTransactionInfo::class.java)
                transactions.transactions?.add(transactionInfo)
            }

            return transactions
        }
    }
}
