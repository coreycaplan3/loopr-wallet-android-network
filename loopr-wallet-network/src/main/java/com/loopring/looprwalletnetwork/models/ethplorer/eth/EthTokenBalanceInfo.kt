package com.loopring.looprwalletnetwork.models.ethplorer.eth

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.loopring.looprwalletnetwork.extensions.ifNotNullOrEmpty
import io.realm.RealmObject
import java.lang.reflect.Type
import java.math.BigDecimal

/**
 * Created by arknw229 on 3/12/18.
 *
 * Ethplorer API
 *
 * Token balance information for a single token
 *
 * ```
 * {
 *   tokenInfo: # token data (same format as token info),
 *   balance:   # token balance (as is, not reduced to a floating point value),
 *   totalIn:   # total incoming token value
 *   totalOut:  # total outgoing token value
 * }
 * ```
 *
 * @author arknw229
 */
open class EthTokenBalanceInfo : RealmObject() {


    /**
     * Token information object
     */
    var tokenInfo: EthTokenInfo? = null

    /**
     * Token balance information
     */
    var balance: BigDecimal?
        get() = mBalance?.let { BigDecimal(it) }
        set(value) {
            mBalance = value?.toPlainString()
        }

    @SerializedName("balance")
    private var mBalance: String? = null

    /**
     * Total tokens of this type that have gone into the given address
     */
    var totalIn: BigDecimal?
        get() = mTotalIn?.let { BigDecimal(it) }
        set(value) {
            mTotalIn = value?.toPlainString()
        }

    @SerializedName("totalIn")
    private var mTotalIn: String? = null

    /**
     * Total tokens of this type that have gone out of the given address
     */
    var totalOut: BigDecimal?
        get() = mTotalOut?.let { BigDecimal(it) }
        set(value) {
            mTotalIn = value?.toPlainString()
        }

    @SerializedName("totalOut")
    private var mTotalOut: String? = null

    /**
     * Deserializer for [EthTokenBalanceInfo]
     * Handles some empty strings and inconsistencies in the API responses
     */
    class EthTokenBalanceInfoDeserializer : JsonDeserializer<EthTokenBalanceInfo> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EthTokenBalanceInfo? {
            if (json.isJsonNull || json.isJsonPrimitive) {
                return null
            } else {
                val jsonObj = json.asJsonObject
                val balanceInfo = EthTokenBalanceInfo()

                jsonObj.ifNotNullOrEmpty(EthTokenBalanceInfo::tokenInfo) {
                    balanceInfo.tokenInfo = context.deserialize(jsonObj.get(it), EthTokenInfo::class.java)
                }
                jsonObj.ifNotNullOrEmpty(EthTokenBalanceInfo::balance) {
                    balanceInfo.balance = BigDecimal(jsonObj.get(it).asString)
                }
                jsonObj.ifNotNullOrEmpty(EthTokenBalanceInfo::totalIn) {
                    balanceInfo.totalIn = BigDecimal(jsonObj.get(it).asString)
                }
                jsonObj.ifNotNullOrEmpty(EthTokenBalanceInfo::totalOut) {
                    balanceInfo.totalOut = BigDecimal(jsonObj.get(it).asString)
                }

                return balanceInfo
            }
        }
    }
}