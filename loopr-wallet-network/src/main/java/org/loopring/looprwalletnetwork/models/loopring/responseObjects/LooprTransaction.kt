package org.loopring.looprwalletnetwork.models.loopring.responseObjects

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigInteger
import java.util.*

open class LooprTransaction : RealmObject() {

    /**
     * Owner of the transaction
     * Example output - "0x66727f5DE8Fbd651Dc375BB926B16545DeD71EC9"
     */
    @SerializedName("owner")
    var owner: String? = null

    /**
     * The transaction sender
     * Example output - "0x66727f5DE8Fbd651Dc375BB926B16545DeD71EC9"
     */
    @SerializedName("from")
    var from: String? = null

    /**
     * The transaction receiver
     * Example output - "0x23605cD09677600A91Df271C86E290cb09a17eeD"
     */
    @SerializedName("to")
    var to: String? = null

    /**
     * The timestamp of transaction create time
     * Example output - 150134131
     */
    @SerializedName("createTime")
    var createTime: Date? = null

    /**
     * The timestamp of transaction update time
     * Example output - 150134131
     */
    @SerializedName("updateTime")
    var updateTime: Date? = null

    /**
     * The transaction hash
     * Example output - "0xa226639a5852df7a61a19a473a5f6feb98be5247077a7b22b8c868178772d01e"
     */
    @SerializedName("hash")
    var hash: String? = null

    /**
     * The number of the block which contains the transaction
     * Example output - 5029675
     */
    @SerializedName("blockNumber")
    var blockNumber: Long? = null

    /**
     * The amount of transaction involved
     * Example output - "0x0000000a7640001"
     */
    var value: BigInteger?
        get() {
            return mValue?.let{ BigInteger(it, 16)}
        }
        set(value) {
            mValue = value?.toString(16)
        }

    private var mValue: String? = null

    /**
     * The transaction type, like wrap/unwrap, transfer/receive
     * Example output - "WRAP"
     */
    @SerializedName("type")
    var type: String? = null

    /**
     * The current transaction status
     * Example output - "PENDING"
     */
    @SerializedName("status")
    var status: String? = null

    /**
     * Custom class deserializer
     */
    class LooprTransactionDeserializer : JsonDeserializer<LooprTransaction> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LooprTransaction? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val transaction = LooprTransaction()

                //TODO - check if this code is enough to handle normally encountered errors
                jsonObj.get("owner")?.let {
                    transaction.owner = it.asString
                }

                jsonObj.get("from")?.let {
                    transaction.from  = it.asString
                }

                jsonObj.get("to")?.let {
                    transaction.to  = it.asString
                }

                jsonObj.get("from")?.let {
                    transaction.from  = it.asString
                }

                jsonObj.get("createTime")?.let {
                    transaction.createTime  = Date(it.asString.toLong())
                }

                jsonObj.get("updateTime")?.let {
                    transaction.updateTime  = Date(it.asString.toLong())
                }

                jsonObj.get("hash")?.let {
                    transaction.hash  = it.asString
                }

                jsonObj.get("blockNumber")?.let {
                    transaction.blockNumber  = it.asLong
                }

                jsonObj.get("value")?.let {
                    transaction.mValue  = it.asString
                }

                jsonObj.get("type")?.let {
                    transaction.type  = it.asString
                }

                jsonObj.get("status")?.let {
                    transaction.status  = it.asString
                }


                return transaction
            }
        }

    }

}