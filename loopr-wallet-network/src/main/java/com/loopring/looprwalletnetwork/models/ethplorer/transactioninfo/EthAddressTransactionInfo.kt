package com.loopring.looprwalletnetwork.models.ethplorer.transactioninfo

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.extensions.ifNotNullOrEmpty
import io.realm.RealmObject
import retrofit2.Response.success
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/13/18.
 *
 * Ethplorer API
 *
 * Information about a transaction perfomed by an address
 *
 * limit:   maximum number of operations [1 - 50, default = 10]
 *
 * showZeroValues:  show transactions with zero ETH value, default = 0
 *
 * ```
 * {
 *   timestamp:       # operation timestamp
 *   from:            # source address (if two addresses involved),
 *   to:              # destination address (if two addresses involved),
 *   hash:            # transaction hash
 *   value:           # ETH value (as is, not reduced to a floating point value),
 *   input:           # input data
 *   success:         # true if transactions was completed, false if failed
 * }
 * ```
 *
 * @author arknw229
 */
open class EthAddressTransactionInfo : RealmObject() {


    /**
     * Timestamp of the transaction
     */
    var timestamp: Long? = null

    /**
     * Who the transaction is from (if there is more than one address involved)
     */
    var from: String? = null

    /**
     * Who the transaction is going to (if there is more than one address involved)
     */
    var to: String? = null

    /**
     * Transaction hash
     */
    var hash: String? = null

    /**
     * The Ether value of the transaction
     */
    var value: BigDecimal?
        get() = mValue?.let { BigDecimal(it) }
        set(value) {
            mValue = value?.toPlainString()
        }

    @SerializedName("value")
    var mValue: String? = null

    /**
     * Input for the transaction
     * Will often be "0x" or null is only used if a smart contract is involved
     */
    var input: String? = null

    /**
     * Success or failure of the transaction
     */
    @SerializedName("success")
    var isSuccess: Boolean? = null


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

                jsonObj.ifNotNullOrEmpty(EthAddressTransactionInfo::timestamp) {
                    addressTransactions.timestamp = jsonObj.get(it).asString.toLongOrNull()
                }
                jsonObj.ifNotNullOrEmpty(EthAddressTransactionInfo::from) {
                    addressTransactions.from = jsonObj.get(it).asString
                }
                jsonObj.ifNotNullOrEmpty(EthAddressTransactionInfo::to) {
                    addressTransactions.to = jsonObj.get(it).asString
                }
                jsonObj.ifNotNullOrEmpty(EthAddressTransactionInfo::hash) {
                    addressTransactions.hash = jsonObj.get(it).asString
                }
                jsonObj.ifNotNullOrEmpty(EthAddressTransactionInfo::value) {
                    addressTransactions.value = BigDecimal(jsonObj.get(it).asString)
                }
                jsonObj.ifNotNullOrEmpty(EthAddressTransactionInfo::input) {
                    if (jsonObj.get(it).asString != "0x") {
                        addressTransactions.input = jsonObj.get(it).asString
                    }
                }
                jsonObj.ifNotNullOrEmpty("success") {
                    addressTransactions.isSuccess = jsonObj.get(it).asString?.toBoolean()
                }

                return addressTransactions
            }
        }
    }
}
